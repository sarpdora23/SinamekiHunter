package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class Intruder implements ControllersParent{
    private RequestModel requestModel;
    @FXML
    private TextArea requestTextArea;
    @FXML
    private Label wordListNameLabel;
    @FXML
    private ListView wordsListView;
    @FXML
    private Label speedLabel;
    public void setRequestModel(RequestModel requestModel){

    }
    @Override
    public void InitController() {

    }
    @FXML
    protected void addFuzzParam(){

    }
    @FXML
    protected void deleteFuzzParam(){

    }
    @FXML
    protected void selectWordList(){

    }
    @FXML
    protected void startFuzzing(){

    }

}
