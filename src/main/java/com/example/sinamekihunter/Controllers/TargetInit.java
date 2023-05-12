package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TargetInit implements ControllersParent{
    @FXML
    private TextField targetTextField;
    @FXML
    private Label errorTextLabel;

    @Override
    public void InitController() {

    }
    @FXML
    protected void onStartAttack() throws IOException {
        String targetUrl = targetTextField.getText();
        Pattern urlPattern = Pattern.compile("^https?://");
        Matcher matcher = urlPattern.matcher(targetUrl);
        if (matcher.find()){
            TargetModel.createInstance(targetUrl);
            FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.MAIN_DASHBOARD_FXML));
            Stage stage = new Stage();
            Scene main_scene = new Scene(fxmlLoader.load());
            MainDashboard mainDashboard = fxmlLoader.getController();
            stage.setScene(main_scene);
            StageManager.getInstance().createStage(StringValues.StageNames.MAIN_DASHBOARD_STAGE,
                    stage,StringValues.SceneNames.MAIN_DASHBOARD_SCENE,mainDashboard);

        }
        else{
            errorTextLabel.setText("Url must be this form: http://example.com");
        }
    }
}
