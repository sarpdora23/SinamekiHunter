package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.ResponseModel;
import com.example.sinamekihunter.Utils.ColorValues;
import com.example.sinamekihunter.Utils.StringValues;
import com.example.sinamekihunter.Vulnerabilities.ControlledVuln;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.css.Selector;
import javafx.css.Style;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.skin.TextAreaSkin;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;


import java.util.Collection;
import java.util.Collections;

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
    @FXML
    private ScrollPane request_text_parent;

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
        for (ControlledVuln controlledVuln: this.requestModel.getControlledVulns()) {

            if (controlledVuln.getRequest_found_part() != null){
                int startIndex = this.requestModel.getRequestText().indexOf(controlledVuln.getRequest_found_part());
                int endIndex = startIndex + controlledVuln.getRequest_found_part().length();
                System.out.println(controlledVuln.getRequest_found_part());
                String color = "";
                if (controlledVuln.getVuln_name() == StringValues.VulnValues.LFI){
                    color = "purple";
                }else if(controlledVuln.getVuln_name() == StringValues.VulnValues.OPEN_REDIRECT){
                    color = "aliceblue";
                }else if(controlledVuln.getVuln_name() == StringValues.VulnValues.SSRF){
                    color = "green";
                }else if(controlledVuln.getVuln_name() == StringValues.VulnValues.SSTI){
                    color = "yellow";
                }else if(controlledVuln.getVuln_name() == StringValues.VulnValues.XSS){
                    color = "red";
                }
                request_content_textarea.setStyle("-fx-highlight-fill: "+color+"; -fx-highlight-text-fill: white;");
                request_content_textarea.selectRange(startIndex,endIndex);
            }
            if (controlledVuln.getResponse_found_part() != null){
                int startIndex = this.requestModel.responseModel.getContentString().indexOf(controlledVuln.getResponse_found_part());
                int endIndex = startIndex + controlledVuln.getResponse_found_part().length();
                String color = "";
                response_content_textarea.setStyle("-fx-highlight-fill: "+color+"; -fx-highlight-text-fill: white;");
                response_content_textarea.selectRange(startIndex,endIndex);
            }
        }
        System.out.println("Stage Name: " + StringValues.StageNames.REQUEST_DETAIL_VIEW_STAGE + requestModel.getUid());
        //TODO POTANSIYEL ZAAFİYET STİCKERLARINI HBOX'A EKLE
        //TODO REQUEST RESPONSE TEXTARELARDA HIGHLIGHT İŞLEMİ YAP
    }
}
