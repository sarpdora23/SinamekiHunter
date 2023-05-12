package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class Intruder implements ControllersParent{
    @FXML
    private TextArea requestTextArea;
    @FXML
    private Label wordListNameLabel;
    @FXML
    private ListView wordsListView;
    @FXML
    private ChoiceBox fuzzTypeChoiceBox;
    @FXML
    private Label speedLabel;
    private String attackType;
    public void setRequest(String requestText){
        requestTextArea.setText(requestText);
    }
    @Override
    public void InitController() {
        ArrayList typeList = new ArrayList<>();
        typeList.add("Sniper");
        typeList.add("Battering Ram");
        typeList.add("Pitchfork");
        typeList.add("Clusterbomb");
        fuzzTypeChoiceBox.setItems(FXCollections.observableList(typeList));
        fuzzTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            System.out.println(newValue);
        });
    }
    @FXML
    protected void addFuzzParam(){
        String requestText = requestTextArea.getText();
        IndexRange selectionRange = requestTextArea.getSelection();
        String newRequestText = requestText.substring(0, selectionRange.getStart()) + "FUZZ" + requestText.substring(selectionRange.getEnd());
        requestTextArea.setText(newRequestText);
    }
    @FXML
    protected void deleteFuzzParam(){
        String requestText = requestTextArea.getText();
        if (requestText.indexOf("FUZZ") != -1){
            String newRequestText = requestText.replace("FUZZ","");
            requestTextArea.setText(newRequestText);
        }

    }
    @FXML
    protected void selectWordList(){

    }
    @FXML
    protected void startFuzzing(){

    }

}
