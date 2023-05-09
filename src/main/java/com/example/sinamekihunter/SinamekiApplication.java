package com.example.sinamekihunter;

import com.example.sinamekihunter.Controllers.ControllersParent;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.VulnModel;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SinamekiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.TARGET_INIT_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(StringValues.ApplicationValues.MAIN_WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();
        //test();
        init_values(stage,scene,fxmlLoader.getController());
    }
    public static void main(String[] args) {
        launch();
    }
    private void init_values(Stage first_stage, Scene first_scene, ControllersParent first_controller){
        StageManager.createInstance(first_stage,StringValues.StageNames.TARGET_INIT_STAGE);
        ControllersManager.createInstance(first_controller,first_scene,StringValues.SceneNames.TARGET_INIT_SCENE);
        initVulnModels();
    }
    private void initVulnModels(){
        VulnModel.getLFI();
        VulnModel.getSSRF();
        VulnModel.getOpenRedirect();
        VulnModel.getSSTI();
        VulnModel.getXSS();
    }
    private void test() throws IOException {
        Pattern pattern = Pattern.compile("agent \\d\\d\\d");
        Matcher matcher = pattern.matcher("agent 007");
        System.out.println(matcher.find());
    }
}