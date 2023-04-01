package com.example.sinamekihunter;

import com.example.sinamekihunter.Controllers.ControllersParent;
import com.example.sinamekihunter.Controllers.TargetInitController;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Application;
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
        RequestModel requestModel = new RequestModel("https://www.google.com.tr/?hl=tr");
        requestModel.addHeader("Test","Test");
        requestModel.addHeader("Deneme","Merhaba");
        //NetworkFunctions.sendRequest(requestModel);
        //test(stage,10);
    }

    public static void main(String[] args) {
        launch();
    }
    private void init_values(Stage first_stage, Scene first_scene, ControllersParent first_controller){
        StageManager.createInstance(first_stage,StringValues.StageNames.TARGET_INIT_STAGE);
        ControllersManager.createInstance(first_controller,first_scene,StringValues.SceneNames.TARGET_INIT_SCENE);
    }
    private void test(Stage stage,int a) throws FileNotFoundException, InterruptedException {
        FileChooser fileChooser = new FileChooser();
        File wordlist_file = fileChooser.showOpenDialog(stage);
        if (wordlist_file != null){
            Scanner scanner = new Scanner(wordlist_file);
            while (scanner.hasNext()){
                int counter = 0;
                ArrayList<String> words = new ArrayList<>();
                while (scanner.hasNext() && counter < a){
                    words.add(scanner.nextLine());
                    counter++;
                }
                for (String word: words) {
                    Test test = new Test(word);
                    test.start();
                }
                Thread.sleep(2000);
            }
        }
    }
}
class Test extends Thread{
    private String word;
    public Test(String word){
        this.word = word;
    }
    @Override
    public void run(){
        System.out.println(word);
    }
}