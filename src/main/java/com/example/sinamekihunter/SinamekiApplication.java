package com.example.sinamekihunter;

import com.example.sinamekihunter.Controllers.ControllersParent;
import com.example.sinamekihunter.Controllers.TargetInitController;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Network.Proxy;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import com.example.sinamekihunter.Utils.URLParseFunctions;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SinamekiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.TARGET_INIT_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(StringValues.ApplicationValues.MAIN_WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();
        test();
        init_values(stage,scene,fxmlLoader.getController());
    }
    public static void main(String[] args) {
        launch();
    }
    private void init_values(Stage first_stage, Scene first_scene, ControllersParent first_controller){
        StageManager.createInstance(first_stage,StringValues.StageNames.TARGET_INIT_STAGE);
        ControllersManager.createInstance(first_controller,first_scene,StringValues.SceneNames.TARGET_INIT_SCENE);
    }
    private void test() throws IOException {
        String url = "http://nahamstore.thm/product/test?id=1&name=Hoodie+%2B+Tee";
        String endpoint = url.substring(0,url.indexOf('?'));
        System.out.println(endpoint);
    }
}