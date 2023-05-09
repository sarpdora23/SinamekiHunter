package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.ColorValues;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class RequestRootView implements ControllersParent{
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
    @FXML
    private GridPane vuln_sticker_pane;
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
        int current_row = 0;
        int current_column = 0;
        for (String vuln: this.requestModel.getPossibleVulns()) {
            Label vuln_sticker = new Label(vuln);
            vuln_sticker.setFont(Font.font(vuln_sticker.getFont().getName(), FontWeight.BOLD,10));
            vuln_sticker.setTextFill(Color.WHITE);
            vuln_sticker.setMinWidth(32);
            vuln_sticker.setAlignment(Pos.CENTER);
            if (vuln == StringValues.VulnValues.LFI){
                vuln_sticker.setBackground(new Background(new BackgroundFill(ColorValues.VULN_STICKER.LFI,new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            }
            else if(vuln == StringValues.VulnValues.OPEN_REDIRECT){
                vuln_sticker.setBackground(new Background(new BackgroundFill(ColorValues.VULN_STICKER.OPEN_REDIRECT,new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            }
            else if(vuln == StringValues.VulnValues.SSRF){
                vuln_sticker.setBackground(new Background(new BackgroundFill(ColorValues.VULN_STICKER.SSRF,new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            }
            else if(vuln == StringValues.VulnValues.SSTI){
                vuln_sticker.setBackground(new Background(new BackgroundFill(ColorValues.VULN_STICKER.SSTI,new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            } else if (vuln == StringValues.VulnValues.XSS) {
                vuln_sticker.setBackground(new Background(new BackgroundFill(ColorValues.VULN_STICKER.XSS,new CornerRadii(4,4,4,4,false), Insets.EMPTY)));
            }
            this.vuln_sticker_pane.add(vuln_sticker,current_row,current_column);
            current_column++;
            if (current_column == 3){
                current_row++;
                current_column = 0;
            }
        }
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
    @FXML
    protected void showDetails() throws IOException {
        Stage detail_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.REQUEST_DETAIL_VIEW_FXML));
        Scene request_detail_scene = new Scene(fxmlLoader.load());
        RequestDetail requestDetailController = fxmlLoader.getController();
        requestDetailController.setRequestModel(requestModel);
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
    @FXML
    public void sendRepeater() throws IOException {
        MainDashboard mainDashboardController = (MainDashboard) ControllersManager.getInstance().getController(StringValues.SceneNames.MAIN_DASHBOARD_SCENE);
        if (!mainDashboardController.isRepeaterOpen){
            mainDashboardController.openRepeater();
        }
        Repeater repeaterController = (Repeater) ControllersManager.getInstance().getController(StringValues.SceneNames.REPEATER_VIEW_SCENE);
        repeaterController.setRequest(requestModel.getRequestText());
    }
    @FXML
    protected void sendIntruder(){
        //TODO INTRUDER'A GÖNDER
    }
}
