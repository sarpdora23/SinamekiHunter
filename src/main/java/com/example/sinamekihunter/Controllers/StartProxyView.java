package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Network.Proxy;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StartProxyView implements ControllersParent{

     @FXML
     private Button startButton;
     @FXML
     private Label result_message_label;
     @FXML
     private TextField port_textfield;

    @Override
    public void InitController() {
        if (Proxy.getInstance() != null){
            if(Proxy.getInstance().isRunning()){
                port_textfield.setDisable(true);
                startButton.setDisable(true);
                result_message_label.setText("Proxy is running....");
            }
        }

    }
    @FXML
    protected void startProxy() throws IOException {
        int portNumber = Integer.valueOf(port_textfield.getText());
        Proxy.setProxyUp(portNumber);
        Proxy.getInstance().startServer();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    port_textfield.setDisable(true);
                    startButton.setDisable(true);
                    Thread.sleep(1500);
                    result_message_label.setText("Proxy is running...");
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                StageManager.getInstance().getStage(StringValues.StageNames.PROXY_START_VIEW_STAGE).close();
            }
        });
    }
}
