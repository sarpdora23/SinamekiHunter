package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
    @FXML
    private Pane request_root_pane;
    @Override
    public void InitController() {

    }

    public void setRequestModel(RequestModel requestModel) {
        this.requestModel = requestModel;
        setPaneMethodColor(requestModel);
        request_method_label.setText(requestModel.getRequest_method());
        request_method_label.setStyle("-fx-text-fill: white;");
        if(requestModel.getUrl().length() > 40){endpoint_label.setText(requestModel.getUrl().substring(0,40) + "...");}
        else{endpoint_label.setText(requestModel.getUrl());}
        endpoint_label.setStyle("-fx-font-weight: bold");
        status_code_label.setText(String.valueOf(requestModel.getResponse().getStatusCode()));
        length_label.setText(String.valueOf(requestModel.responseModel.getContentLength()));

    }
    public RequestModel getRequestModel(){return this.requestModel;}
    private void setPaneMethodColor(RequestModel requestModel){

        if(requestModel.getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_GET){
            request_method_label.setBackground(new Background(new BackgroundFill(Color.rgb(97, 175, 254),new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            request_root_pane.setBackground(new Background(new BackgroundFill(Color.rgb(235, 243, 251),new CornerRadii(0,0,0,0,false), Insets.EMPTY)));
            request_root_pane.setBorder(new Border((new BorderStroke(Color.rgb(97, 175, 254), BorderStrokeStyle.SOLID, null, null))));
        }
        else if(requestModel.getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_POST){
            request_method_label.setBackground(new Background(new BackgroundFill(Color.rgb(73, 204, 144),new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            request_root_pane.setBackground(new Background(new BackgroundFill(Color.rgb(232, 246, 240),new CornerRadii(0,0,0,0,false), Insets.EMPTY)));
            request_root_pane.setBorder(new Border((new BorderStroke(Color.rgb(73, 204, 144), BorderStrokeStyle.SOLID, null, null))));
        }
        else if(requestModel.getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_PUT){
            request_method_label.setBackground(new Background(new BackgroundFill(Color.rgb(252, 161, 48),new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            request_root_pane.setBackground(new Background(new BackgroundFill(Color.rgb(251, 241, 230),new CornerRadii(0,0,0,0,false), Insets.EMPTY)));
            request_root_pane.setBorder(new Border((new BorderStroke(Color.rgb(252, 161, 48), BorderStrokeStyle.SOLID, null, null))));
        }
        else if(requestModel.getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_HEAD){
            request_method_label.setBackground(new Background(new BackgroundFill(Color.rgb(144, 18, 254),new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            request_root_pane.setBackground(new Background(new BackgroundFill(Color.rgb(244, 231, 255),new CornerRadii(0,0,0,0,false), Insets.EMPTY)));
            request_root_pane.setBorder(new Border((new BorderStroke(Color.rgb(144, 18, 254), BorderStrokeStyle.SOLID, null, null))));
        }
        else if(requestModel.getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_DELETE){
            request_method_label.setBackground(new Background(new BackgroundFill(Color.rgb(249, 62, 62),new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            request_root_pane.setBackground(new Background(new BackgroundFill(Color.rgb(254, 235, 235),new CornerRadii(0,0,0,0,false), Insets.EMPTY)));
            request_root_pane.setBorder(new Border((new BorderStroke(Color.rgb(249, 62, 62), BorderStrokeStyle.SOLID, null, null))));
        }
        else if(requestModel.getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_OPTIONS){
            request_method_label.setBackground(new Background(new BackgroundFill(Color.rgb(97, 175, 254),new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            request_root_pane.setBackground(new Background(new BackgroundFill(Color.rgb(235, 243, 251),new CornerRadii(0,0,0,0,false), Insets.EMPTY)));
            request_root_pane.setBorder(new Border((new BorderStroke(Color.rgb(97, 175, 254), BorderStrokeStyle.SOLID, null, null))));
        }
        else {
            request_method_label.setBackground(new Background(new BackgroundFill(Color.rgb(97, 175, 254),new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            request_root_pane.setBackground(new Background(new BackgroundFill(Color.rgb(235, 243, 251),new CornerRadii(0,0,0,0,false), Insets.EMPTY)));
            request_root_pane.setBorder(new Border((new BorderStroke(Color.rgb(97, 175, 254), BorderStrokeStyle.SOLID, null, null))));
        }
    }
}
