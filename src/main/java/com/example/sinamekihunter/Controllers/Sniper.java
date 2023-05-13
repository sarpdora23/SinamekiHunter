package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Managers.StageManager;
import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Sniper implements IntruderType{
    @FXML
    private Label wordListNameLabel;
    @FXML
    private ListView wordsListView;
    private File wordList;
    private ObservableList wordObservableList = FXCollections.observableList(new ArrayList<>());
    private int speed = 1;
    public boolean isStopped;
    private boolean isRunning;
    private int totalWordCount = 0;

    @Override
    public void fuzz(String _requestText) {
        this.totalWordCount = wordObservableList.size();
        Thread bigThread = new Thread(){
            @Override
            public void run(){
                for (int i = 0; i < wordObservableList.size() - speed; i+=speed) {
                    int finalI = i;
                    if(isStopped){
                        isRunning = false;
                        break;
                    }
                    else{
                        Thread threadGroup = new Thread(){
                            @Override
                            public void run(){
                                for (int j = finalI; j <finalI + speed; j++) {
                                    int finalJ = j;
                                    Thread threadPiece = new Thread(){
                                        @Override
                                        public void run(){
                                            String word = wordObservableList.get(finalJ).toString();
                                            String requestText = _requestText.replace("FUZZ",word);
                                            RequestModel requestModel = NetworkFunctions.stringToRequestModel(requestText, StringValues.NetworkValues.REQUEST_TYPE_INTRUDER);
                                            requestModel.setWord(word);
                                            try {
                                                NetworkFunctions.sendRequest(requestModel);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    };
                                    threadPiece.start();
                                }
                            }
                        };
                        threadGroup.start();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        };
        bigThread.start();
    }

    @Override
    public File getWordlist() {
        return this.wordList;
    }

    @Override
    public boolean getIsRunning() {
        return this.isRunning;
    }

    @Override
    public boolean getIsStopped() {
        return this.isStopped;
    }

    @Override
    public void setRunning(boolean newValue) {
        this.isRunning = newValue;
    }

    @Override
    public void setStopped(boolean newValue) {
        this.isStopped = newValue;
    }

    @Override
    public int getTotalWordCount() {
        return this.wordObservableList.size();
    }

    @Override
    public void initIntruder() {
        wordsListView.setItems(wordObservableList);

    }

    @Override
    public void setSpeed(int newValue) {
        this.speed = newValue;
    }

    @FXML
    protected void selectWordList() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File wordlist_file = fileChooser.showOpenDialog(StageManager.getInstance().getStage(StringValues.StageNames.INTRUDER_VIEW_STAGE));
        if (wordlist_file != null){
            wordListNameLabel.setText(wordlist_file.getPath());
            this.wordList = wordlist_file;
            FileReader fileReader = new FileReader(wordList);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                wordObservableList.add(line);
                totalWordCount++;
            }
            bufferedReader.close();
            fileReader.close();
        }
    }
}
