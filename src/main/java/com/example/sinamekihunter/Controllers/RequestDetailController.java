package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.ResponseModel;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class RequestDetailController implements ControllersParent{

    @FXML
    private TextArea request_content_textarea;
    @FXML
    private TextArea response_content_textarea;
    @FXML
    private ScrollPane response_text_parent;
    @FXML
    private ToggleButton response_raw_toggle_button;
    @FXML
    private WebView response_webview;
    @FXML
    private HBox vuln_sticker_hbox;

    private RequestModel requestModel;
    private ResponseModel responseModel;

    @Override
    public void InitController() {

    }
    public void setRequestModel(RequestModel requestModel){
        this.requestModel = requestModel;
        this.responseModel = requestModel.getResponse();
        request_content_textarea.setText(this.requestModel.getRequestText());
        response_content_textarea.setText(this.responseModel.getContentString());
        response_webview.getEngine().loadContent(this.responseModel.getContentString());
        response_raw_toggle_button.setSelected(true);
        response_raw_toggle_button.selectedProperty().addListener((observable,oldValue,newValue)->{
            if (newValue){
                response_webview.setOpacity(0);
                response_text_parent.setOpacity(1);
            }
            else{
                response_webview.setOpacity(1);
                response_text_parent.setOpacity(0);
            }
        });
        System.out.println("Stage Name: " + StringValues.StageNames.REQUEST_DETAIL_VIEW_STAGE + requestModel.getUid());
        //TODO POTANSIYEL ZAAFİYET STİCKERLARINI HBOX'A EKLE
        //TODO REQUEST RESPONSE TEXTARELARDA HIGHLIGHT İŞLEMİ YAP
    }
}
