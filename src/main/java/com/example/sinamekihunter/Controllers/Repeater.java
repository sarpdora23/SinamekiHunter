package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class Repeater implements ControllersParent{
    @FXML
    private TextArea requestTextArea;
    @FXML
    private TextArea responseTextArea;

    @Override
    public void InitController() {

    }

    public void setRequest(String requestText){
        requestTextArea.setText(requestText);
    }
    @FXML
    protected void sendRequest() throws IOException {
        String requestText = requestTextArea.getText();
        RequestModel requestModel = NetworkFunctions.stringToRequestModel(requestText, StringValues.NetworkValues.REQUEST_TYPE_REPEATER);
        NetworkFunctions.sendRequest(requestModel);
    }
    public void setResponse(String responseText){
        responseTextArea.setText(responseText);

    }
}
