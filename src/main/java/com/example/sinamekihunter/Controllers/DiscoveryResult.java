package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.RequestThreadModel;
import com.example.sinamekihunter.Models.ResponseModel;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.Utils.DiscoverThread;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DiscoveryResult implements ControllersParent {
    private DiscoverThread discoverThread;
    private RequestThreadModel requestThreadModel;
    private int totalWordCount;
    @FXML
    public Label discovery_request_label;
    @FXML
    private Label discovery_type_label;
    @FXML
    private Label discovery_progress_label;
    private ObservableList<ResponseModel> responseModels;
    private ArrayList<ResponseModel> displayResponseModelList;
    private ObservableList<Integer> statusCodesList;
    @FXML
    private Button discovery_stop_button;
    @FXML
    private ListView discovery_result_listview;
    @FXML
    private ProgressBar discovery_progressbar;
    @FXML
    private ToggleButton orderButton;
    @FXML
    private ToggleButton orderMinMax;
    @FXML
    private ChoiceBox statusCodeFilterChoiceBox;
    private boolean isFiltered = false;
    private int filteredValue;

    @Override
    public void InitController() {
        this.discovery_type_label.setText(this.requestThreadModel.getThreadName());
        discovery_request_label.setText("Current Request: ");
        totalWordCount = getTotalWordCount();
        discovery_progress_label.setText("0/"+totalWordCount);
        discoverThread.start();
        responseModels = FXCollections.observableArrayList();
        statusCodesList = FXCollections.observableArrayList();
        displayResponseModelList = new ArrayList<>();
        statusCodeFilterChoiceBox.setItems(statusCodesList);
        discovery_result_listview.setItems(responseModels);
        orderButton.selectedProperty().addListener((observableValue,old,newValue)->{
            orderList();
        });
        orderMinMax.selectedProperty().addListener((observableValue,old,newValue)->{
            orderList();
        });
        statusCodeFilterChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println("Value:" + statusCodesList.get(t1.intValue()));
                isFiltered = true;
                filteredValue = statusCodesList.get(t1.intValue());
                filterList(filteredValue);
            }
        });
    }
    public void setDiscoverThread(DiscoverThread discoverThread, RequestThreadModel requestThreadModel){
        this.discoverThread = discoverThread;
        this.requestThreadModel = requestThreadModel;
    }

    @FXML
    protected void stopDiscovery(){
        System.out.println("Discovery Stopping");
        this.discoverThread.interrupt();
    }
    private int getTotalWordCount(){
        int satirSayisi = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.requestThreadModel.getWordlist()));
            while (reader.readLine() != null) satirSayisi++;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return satirSayisi;
    }
    public void updateRequest(RequestModel requestModel){
        this.requestThreadModel.addCompleteRequest(requestModel);
        int completedRequest = this.requestThreadModel.getCompletedRequestCounter();
        String progress_string = completedRequest + "/" + totalWordCount;
        ResponseModel responseModel = requestModel.getResponse();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                discovery_progress_label.setText(progress_string);
                discovery_progressbar.setProgress((double) completedRequest / totalWordCount);
                discovery_request_label.setText("Current Request: "+requestModel.getWord());
                if(String.valueOf(responseModel.getStatusCode()).substring(0,1) != "4"){
                    displayResponseModelList.add(responseModel);
                    if (isFiltered){
                        if (responseModel.getStatusCode() == filteredValue){
                            responseModels.add(responseModel);
                        }
                    }
                    else{
                        responseModels.add(responseModel);
                    }
                    if (statusCodesList.indexOf(responseModel.getStatusCode()) == -1){
                        statusCodesList.add(responseModel.getStatusCode());
                    }
                    orderList();
                }

            }
        });

    }
    public void orderList(){
        Comparator<ResponseModel> comparator;
        if (orderButton.isSelected()){
            if (orderMinMax.isSelected()){
                comparator = Comparator.comparing(ResponseModel::getStatusCode);
            }
            else{
                comparator = Comparator.comparing(ResponseModel::getStatusCode).reversed();
            }

        }
        else{
            if (orderMinMax.isSelected()){
                comparator = Comparator.comparing(ResponseModel::getContentLength);
            }
            else{
                comparator = Comparator.comparing(ResponseModel::getContentLength).reversed();
            }
        }
        Collections.sort(this.responseModels,comparator);
    }
    public void filterList(int statusCode){
        ArrayList<ResponseModel> filteredResponses = new ArrayList<>();
        for (ResponseModel response:displayResponseModelList) {
            if (response.getStatusCode() == statusCode){
                filteredResponses.add(response);
            }
        }
        responseModels.clear();
        responseModels.addAll(filteredResponses);
    }
    @FXML
    protected void addSubdomain(){
        ResponseModel selectedResponse = (ResponseModel) discovery_result_listview.getSelectionModel().getSelectedItems().get(0);
        String url = selectedResponse.getWord() + "." + TargetModel.getInstance().getPureDomain();
        AddSubdomainTab.addSubdomain(url);
    }
    @FXML
    protected void sendRepeater() throws IOException {
        MainDashboard mainDashboardController = (MainDashboard) ControllersManager.getInstance().getController(StringValues.SceneNames.MAIN_DASHBOARD_SCENE);
        if (!mainDashboardController.isRepeaterOpen){
            mainDashboardController.openRepeater();
        }
        Repeater repeaterController = (Repeater) ControllersManager.getInstance().getController(StringValues.SceneNames.REPEATER_VIEW_SCENE);
        ResponseModel selectedResponse = (ResponseModel) discovery_result_listview.getSelectionModel().getSelectedItems().get(0);
        System.out.println("FLAG:"+selectedResponse.getRequestModel());
        repeaterController.setRequest(NetworkFunctions.requestModelToString(selectedResponse.getRequestModel()));
    }
}

