package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;

public class BatteringRam extends IntruderParent implements MultiParams{
    private int paramCounter = 1;
    @FXML
    private Pane parentPane;
    private Sniper sniperView;
    @Override
    public void fuzz(String requestText) {

        File wordList = sniperView.getWordlist();
        int totalWordCount = sniperView.getTotalWordCount();
        ObservableList wordObservableList = sniperView.getWordObservableList();
        String _requestText = requestText;
        Thread bigThread = new Thread(){
            @Override
            public void run(){
                for (int i = 0; i <= (totalWordCount) - speed; i+=speed) {
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
                                            String requestText = _requestText;
                                            for (int i = 0; i < paramCounter; i++) {
                                               requestText = requestText.replace("FUZZ"+i,word);
                                            }
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
    public void initIntruder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.SNIPER_VIEW_FXML));
        parentPane.getChildren().add(fxmlLoader.load());
        Sniper sniper = fxmlLoader.getController();
        this.sniperView =sniper;
        sniper.initIntruder();
        changeFuzzPart();
    }
    @Override
    public int getTotalWordCount() {
        return sniperView.totalWordCount;
    }
    @Override
    public File getWordlist() {
        return sniperView.getWordlist();
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
