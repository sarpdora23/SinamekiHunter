package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.RequestThreadModel;
import com.example.sinamekihunter.Models.ResponseModel;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.DiscoverThread;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    private HashMap<ResponseModel,RequestModel> responseToRequestMap = new HashMap<>();
    public boolean enableInit = true;
    private boolean isFiltered = false;
    private int filteredValue;
    private int compReq = 0;

    @Override
    public void InitController() {
        if (this.enableInit){
            this.discovery_type_label.setText(this.requestThreadModel.getThreadName());
            discovery_request_label.setText("Current Request: ");
            totalWordCount = getTotalWordCount();
            discovery_progress_label.setText("0/"+totalWordCount);
            discoverThread.start();
        }
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
    public void InitIntruderResult(int totalWordCount){
        this.totalWordCount = totalWordCount;
        this.discovery_type_label.setText("Intruder");
        enableInit = false;
    }
    @FXML
    protected void stopDiscovery(){
        if (enableInit){
            this.discoverThread.interrupt();
        }
        else{
            Intruder intruder = (Intruder) ControllersManager.getInstance().getController(StringValues.SceneNames.INTRUDER_VIEW_SCENE);
            intruder.intruderType.setStopped(true);
        }
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
        responseToRequestMap.put(responseModel,requestModel);
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
    public void updateIntruderRequest(RequestModel requestModel){

        compReq++;
        int completedRequest = compReq;
        String progress_string = completedRequest + "/" + totalWordCount;
        ResponseModel responseModel = requestModel.getResponse();
        responseToRequestMap.put(responseModel,requestModel);
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
        repeaterController.setRequest(NetworkFunctions.requestModelToString(responseToRequestMap.get(selectedResponse)));
    }
    @FXML
    protected void showDetails() throws IOException {
        Stage detail_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.REQUEST_DETAIL_VIEW_FXML));
        Scene request_detail_scene = new Scene(fxmlLoader.load());
        RequestDetail requestDetailController = fxmlLoader.getController();
        ResponseModel selectedResponse = (ResponseModel) discovery_result_listview.getSelectionModel().getSelectedItems().get(0);
        requestDetailController.setRequestModel(responseToRequestMap.get(selectedResponse),true);
        detail_stage.setTitle("Request Detail");
        detail_stage.setScene(request_detail_scene);
        String stage_uid = UUID.randomUUID().toString();
        StageManager.getInstance().createStage(StringValues.StageNames.REQUEST_DETAIL_VIEW_STAGE +stage_uid,
                detail_stage,
                StringValues.SceneNames.REQUEST_DETAIL_VIEW_SCENE + stage_uid,
                requestDetailController);
        detail_stage.setOnCloseRequest(event -> {
            StageManager.getInstance().closeStage(StringValues.StageNames.REQUEST_DETAIL_VIEW_STAGE + stage_uid);
        });
        detail_stage.show();
    }
}

