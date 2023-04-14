package com.example.sinamekihunter;

import com.example.sinamekihunter.Controllers.ControllersParent;
import com.example.sinamekihunter.Controllers.TargetInitController;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Network.Proxy;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SinamekiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.TARGET_INIT_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(StringValues.ApplicationValues.MAIN_WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();
        init_values(stage,scene,fxmlLoader.getController());
        test2();
       // test();
    }
    public static void test2() throws IOException {
        String test = "POST /login HTTP/1.1\r\n" +
                "Host: nahamstore.thm\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/111.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: tr-TR,tr;q=0.8,en-US;q=0.5,en;q=0.3\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 45\r\n" +
                "Origin: http://nahamstore.thm\r\n" +
                "Connection: close\r\n" +
                "Referer: http://nahamstore.thm/login\r\n" +
                "Cookie: session=22e96394cb85467aebb7500c70307628\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "\r\n" +
                "login_email=aa%40aa.com&login_password=aaaaaa";
       RequestModel requestModel = NetworkFunctions.stringToRequestModel(test);
       NetworkFunctions.sendRequest(requestModel);
    }
    public static void main(String[] args) {
        launch();
    }
    private void init_values(Stage first_stage, Scene first_scene, ControllersParent first_controller){
        StageManager.createInstance(first_stage,StringValues.StageNames.TARGET_INIT_STAGE);
        ControllersManager.createInstance(first_controller,first_scene,StringValues.SceneNames.TARGET_INIT_SCENE);
    }
    private void test() throws IOException {
        Proxy.setProxyUp(8085);
        Proxy proxy = Proxy.getInstance();
        proxy.startServer();
      //  proxy.start(8085);
    }
}