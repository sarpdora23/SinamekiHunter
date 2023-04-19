package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RequestRootViewController implements ControllersParent{
    private RequestModel requestModel;
    @FXML
    private Label endpoint_label;
    @FXML
    private Label status_code_label;
    @FXML
    private Label length_label;
    @FXML
    private Label request_method_label;
    @Override
    public void InitController() {

    }

    public void setRequestModel(RequestModel requestModel) {
        this.requestModel = requestModel;
        request_method_label.setText(requestModel.getRequest_method());
        endpoint_label.setText(requestModel.getUrl());
        status_code_label.setText(String.valueOf(requestModel.getResponse().getStatusCode()));
        length_label.setText(String.valueOf(requestModel.responseModel.getContentLength()));
    }
    public RequestModel getRequestModel(){return this.requestModel;}
}
