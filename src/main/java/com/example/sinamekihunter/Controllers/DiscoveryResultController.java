package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.RequestThreadModel;
import com.example.sinamekihunter.Models.ResponseModel;
import com.example.sinamekihunter.Utils.DiscoverThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class DiscoveryResultController implements ControllersParent {
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


    @Override
    public void InitController() {
        this.discovery_type_label.setText(this.requestThreadModel.getThreadName());
        discovery_request_label.setText("Current Request: ");
        totalWordCount = getTotalWordCount();
        discovery_progress_label.setText("0/"+totalWordCount);
        discoverThread.start();
        responseModels = FXCollections.observableArrayList();
        discovery_result_listview.setItems(responseModels);
        orderButton.selectedProperty().addListener((observableValue,old,newValue)->{
            orderList();
        });
        orderMinMax.selectedProperty().addListener((observableValue,old,newValue)->{
            orderList();
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
                    responseModels.add(responseModel);
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
}

