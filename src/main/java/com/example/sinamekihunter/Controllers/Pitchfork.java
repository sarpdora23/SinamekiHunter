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
import java.util.ArrayList;

public class Pitchfork extends IntruderParent implements MultiParams{
    private int paramCounter = 0;
    @FXML
    private HBox sniperHbox;
    private ArrayList<Sniper> sniperList = new ArrayList<Sniper>();
    @Override
    public void fuzz(String requestText) {
        Sniper sniperView = sniperList.get(0);
        File wordList = sniperView.getWordlist();
        int totalWordCount = sniperView.getTotalWordCount();
        ObservableList wordObservableList = sniperView.getWordObservableList();
        String _requestText = requestText;
        Thread bigThread = new Thread(){
            @Override
            public void run(){
                for (int i = 0; i < (totalWordCount) - speed; i+=speed) {
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
      /*  for (int i = 0; i < paramCounter; i++) {
            changeFuzzPart(true);
        }*/

    }
    @Override
    public int getTotalWordCount() {
        int i = 0;
        for (Sniper sniperPayload:sniperList) {
            if (sniperPayload.getTotalWordCount() > i){
                i=sniperPayload.getTotalWordCount();
            }
        }
        return i;
    }
    @Override
    public File getWordlist() {
        boolean flag = true;
        for (Sniper sniper:sniperList) {
            if (sniper.getWordlist() == null){
                return null;
            }
        }
        return sniperList.get(0).getWordlist();
    }
    @Override
    public void getParamCounter(int paramCounter) {
        while (paramCounter != this.paramCounter){
            try{
                if (paramCounter > this.paramCounter){
                    changeFuzzPart(true);
                    this.paramCounter++;
                }
                else{
                    changeFuzzPart(false);
                    this.paramCounter--;
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

    }
    private void changeFuzzPart(boolean increased) throws IOException {
        if (increased){
            String paramName = "FUZZ" + this.paramCounter;
            FXMLLoader fxmlLoader = new FXMLLoader(SinamekiApplication.class.getResource(StringValues.FXMLNames.SNIPER_VIEW_FXML));
            sniperHbox.getChildren().add(fxmlLoader.load());
            Sniper sniperView = fxmlLoader.getController();
            sniperView.initIntruder();
            sniperView.setParamName(paramName);
            sniperList.add(sniperView);

        }
        else {
            sniperHbox.getChildren().remove(sniperHbox.getChildren().size() - 1);
            sniperList.remove(sniperList.size() -1);
        }

    }
}
