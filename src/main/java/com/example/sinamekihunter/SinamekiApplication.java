package com.example.sinamekihunter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SinamekiApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource("target-init-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sinameki Hunter");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}