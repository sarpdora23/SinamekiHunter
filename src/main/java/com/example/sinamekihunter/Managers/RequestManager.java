package com.example.sinamekihunter.Managers;

import com.example.sinamekihunter.Controllers.DiscoveryResultController;
import com.example.sinamekihunter.Models.RequestThreadModel;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.DiscoverThread;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class RequestManager {
    private static RequestManager instance = new RequestManager();
    private HashMap<String, RequestThreadModel> runningThreads;
    private RequestManager(){
        runningThreads = new HashMap<>();
    }
    public static RequestManager getInstance(){
        return instance;
    }
    public void addThread(RequestThreadModel newThread,String threadName){
        runningThreads.put(threadName,newThread);
    }
    public RequestThreadModel getThread(String threadName){
        return runningThreads.get(threadName);
    }
    public void removeThread(String threadName){
        runningThreads.remove(threadName);
    }
    public void startThread(String url,String threadName, File wordlist,int threadSpeed,String threadMethod,HashMap headerData,HashMap bodyData,HashMap jsonData,String fuzzParam) throws IOException, InterruptedException {
        RequestThreadModel newThread = new RequestThreadModel(threadName,wordlist,threadSpeed,threadMethod);
        Stage resultStage = new Stage();
        DiscoverThread discoverThread = new DiscoverThread(newThread,this,url,headerData,bodyData,jsonData,fuzzParam,threadName);
        FXMLLoader resultViewFXML = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.DISCOVERY_RESULT_VIEW_FXML));
        Scene resultViewScene = new Scene(resultViewFXML.load());
        resultStage.setScene(resultViewScene);
        resultStage.setTitle(StringValues.ApplicationValues.MAIN_WINDOW_TITLE);
        DiscoveryResultController resultController = resultViewFXML.getController();
        resultController.setDiscoverThread(discoverThread,newThread);
        StageManager.getInstance().createStage(StringValues.StageNames.DISCOVERY_RESULT_VIEW_STAGE,resultStage,StringValues.SceneNames.DISCOVERY_RESULT_SCENE,resultController);
        resultStage.setOnCloseRequest(event -> {
            StageManager.getInstance().closeStage(StringValues.StageNames.DISCOVERY_SUBDOMAIN_STAGE);
        });
        resultStage.show();

    }
}

