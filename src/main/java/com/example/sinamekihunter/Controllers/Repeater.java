package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.ControllersManager;
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

    public void setRequest(String requestText)
    {
        requestTextArea.setText(requestText);
    }
    @FXML
    protected void sendRequest() throws IOException {
        String requestText = requestTextArea.getText();
        RequestModel requestModel = NetworkFunctions.stringToRequestModel(requestText, StringValues.NetworkValues.REQUEST_TYPE_REPEATER);
        NetworkFunctions.sendRequest(requestModel);
    }
    @FXML
    protected void sendIntruder() throws IOException {
        MainDashboard mainDashboardController = (MainDashboard) ControllersManager.getInstance().getController(StringValues.SceneNames.MAIN_DASHBOARD_SCENE);
        if (!mainDashboardController.isIntruderOpen){
            mainDashboardController.openIntruder();
        }
        Intruder intruderController = (Intruder) ControllersManager.getInstance().getController(StringValues.SceneNames.INTRUDER_VIEW_SCENE);
        intruderController.setRequest(requestTextArea.getText());
    }
    public void setResponse(String responseText){
        responseTextArea.setText(responseText);

    }
}
