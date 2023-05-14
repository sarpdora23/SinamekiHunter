package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

public class BatteringRam implements IntruderType,MultiParams{
    private int paramCounter = 1;
    @FXML
    private Pane parentPane;
    private Sniper sniperView;
    @Override
    public void fuzz(String requestText) {

    }

    @Override
    public File getWordlist() {
        return null;
    }

    @Override
    public boolean getIsRunning() {
        return false;
    }

    @Override
    public boolean getIsStopped() {
        return false;
    }

    @Override
    public void setRunning(boolean newValue) {

    }

    @Override
    public void setStopped(boolean newValue) {

    }

    @Override
    public int getTotalWordCount() {
        return 0;
    }

    @Override
    public void initIntruder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.SNIPER_VIEW_FXML));
        parentPane.getChildren().add(fxmlLoader.load());
        Sniper sniper = fxmlLoader.getController();
        this.sniperView =sniper;
        changeFuzzPart();
    }

    @Override
    public void setSpeed(int newValue) {

    }

    @Override
    public void getParamCounter(int paramCounter) {
        this.paramCounter = paramCounter;
        changeFuzzPart();
    }
    private void changeFuzzPart(){
        String paramName = "";
        for (int i = 0; i <this.paramCounter ; i++) {
            paramName = paramName + "FUZZ"+i+" ";
        }
        sniperView.setParamName(paramName);
    }
}
