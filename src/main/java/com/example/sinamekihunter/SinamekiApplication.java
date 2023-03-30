package com.example.sinamekihunter;

import com.example.sinamekihunter.Controllers.ControllersParent;
import com.example.sinamekihunter.Controllers.TargetInitController;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SinamekiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.TARGET_INIT_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(StringValues.ApplicationValues.MAIN_WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();
        init_values(stage,scene,fxmlLoader.getController());
    }

    public static void main(String[] args) {
        launch();
    }
    private void init_values(Stage first_stage, Scene first_scene, ControllersParent first_controller){
        StageManager.createInstance(first_stage,StringValues.StageNames.TARGET_INIT_STAGE);
        ControllersManager.createInstance(first_controller,first_scene,StringValues.SceneNames.TARGET_INIT_SCENE);
    }
}