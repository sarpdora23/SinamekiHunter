package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.RequestManager;
import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Intruder implements ControllersParent{
    @FXML
    private TextArea requestTextArea;

    @FXML
    private ChoiceBox fuzzTypeChoiceBox;
    @FXML
    private Label speedLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Slider speedSlider;
    private boolean isFuzzParam = false;
    @FXML
    private Pane typePane;
    private int speed = 1;
    private int paramCounter = 0;

    public IntruderParent intruderType;
    public MultiParams multiParams;
    public void setRequest(String requestText){
        requestTextArea.setText(requestText);
    }
    @Override
    public void InitController() {
        ArrayList typeList = new ArrayList<>();
        typeList.add(StringValues.IntruderTypes.SNIPER);
        typeList.add(StringValues.IntruderTypes.BATTERING_RAM);
        typeList.add(StringValues.IntruderTypes.CLUSTERBOMB);
        typeList.add(StringValues.IntruderTypes.PITCHFORK);
        fuzzTypeChoiceBox.setItems(FXCollections.observableList(typeList));
        fuzzTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (Objects.equals(newValue,StringValues.IntruderTypes.SNIPER)){
                FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.SNIPER_VIEW_FXML));
                try {
                    this.typePane.getChildren().clear();
                    this.typePane.getChildren().add(fxmlLoader.load());
                    this.intruderType = fxmlLoader.getController();
                    this.multiParams = null;
                    this.intruderType.initIntruder();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if (Objects.equals(newValue,StringValues.IntruderTypes.BATTERING_RAM)){
                FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.BATTERING_RAM_VIEW_FXML));
                try {
                    this.typePane.getChildren().clear();
                    this.typePane.getChildren().add(fxmlLoader.load());
                    this.intruderType = fxmlLoader.getController();
                    this.multiParams = fxmlLoader.getController();
                    this.intruderType.initIntruder();
                    this.multiParams.getParamCounter(paramCounter);


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(newValue,StringValues.IntruderTypes.PITCHFORK)) {
                FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.PITCHFORK_VIEW_FXML));
                try {
                    this.typePane.getChildren().clear();
                    this.typePane.getChildren().add(fxmlLoader.load());
                    this.intruderType = fxmlLoader.getController();
                    this.multiParams = fxmlLoader.getController();
                    this.multiParams.getParamCounter(paramCounter);
                    this.intruderType.initIntruder();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (Objects.equals(newValue,StringValues.IntruderTypes.CLUSTERBOMB)) {
                FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.CLUSTERBOMB_VIEW_FXML));
                try {
                    this.typePane.getChildren().clear();
                    this.typePane.getChildren().add(fxmlLoader.load());
                    this.intruderType = fxmlLoader.getController();
                    this.multiParams = fxmlLoader.getController();
                    this.multiParams.getParamCounter(paramCounter);
                    this.intruderType.initIntruder();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            speedLabel.setText(String.valueOf(newValue.intValue()));
            speed = newValue.intValue();
            speedSlider.setValue(newValue.intValue());
            intruderType.speed = speed;

        });
    }
    @FXML
    protected void addFuzzParam(){
        String requestText = requestTextArea.getText();
        IndexRange selectionRange = requestTextArea.getSelection();
        String newRequestText = requestText.substring(0, selectionRange.getStart()) + "FUZZ"+paramCounter + requestText.substring(selectionRange.getEnd());
        int start = selectionRange.getStart();
        int end = selectionRange.getEnd();
        requestTextArea.setText(newRequestText);
        isFuzzParam = true;
        paramCounter++;
        if (multiParams != null){
            multiParams.getParamCounter(paramCounter);
        }

    }
    @FXML
    protected void deleteFuzzParam(){
        if (paramCounter != 0){
            String requestText = requestTextArea.getText();
            if (requestText.indexOf("FUZZ") != -1){
                paramCounter--;
                String newRequestText = requestText.replace("FUZZ"+paramCounter,"");
                requestTextArea.setText(newRequestText);
                isFuzzParam = false;
                if (multiParams != null){
                    multiParams.getParamCounter(paramCounter);
                }
            }
        }
    }
    @FXML
    protected void startFuzzing() throws IOException {
        if (intruderType.getWordlist() != null){
            if (!intruderType.getIsRunning()){
                intruderType.setRunning(true);
                intruderType.setStopped(false);
                Stage resultStage = new Stage();
                FXMLLoader resultViewFXML = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.DISCOVERY_RESULT_VIEW_FXML));
                Scene resultViewScene = new Scene(resultViewFXML.load());
                resultStage.setScene(resultViewScene);
                resultStage.setTitle(StringValues.ApplicationValues.MAIN_WINDOW_TITLE);
                DiscoveryResult discoveryResult = resultViewFXML.getController();
                discoveryResult.InitIntruderResult(intruderType.getTotalWordCount());
                StageManager.getInstance().createStage(StringValues.StageNames.DISCOVERY_RESULT_VIEW_STAGE,resultStage,StringValues.SceneNames.DISCOVERY_RESULT_SCENE,discoveryResult);
                resultStage.setOnCloseRequest(event -> {
                    if (intruderType.getIsRunning()){
                        runningError(true);
                        intruderType.setStopped(true);
                    }
                    StageManager.getInstance().closeStage(StringValues.StageNames.DISCOVERY_RESULT_VIEW_STAGE);
                });
                resultStage.show();
                intruderType.fuzz(requestTextArea.getText());
            }
            else {
                runningError(false);
            }
        }
        else {
            errorLabel.setText("You must select wordlist and FUZZ part.");
        }
    }
    public int getParamCounter(){return this.paramCounter;}
    private void runningError(boolean isStopped){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Intruder");
        alert.setHeaderText("Intruder Alert");
        if (isStopped){
            alert.setContentText("Current Fuzzing process will stop.");
            intruderType.setStopped(true);
            intruderType.setRunning(false);
        }
        else{
            alert.setContentText("Intruder is running! You must stop current process before run new one!");
        }
        alert.showAndWait();
    }
}
