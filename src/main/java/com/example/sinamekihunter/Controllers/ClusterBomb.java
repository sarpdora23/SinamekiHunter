package com.example.sinamekihunter.Controllers;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.SinamekiApplication;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClusterBomb extends IntruderParent implements MultiParams{
    private int paramCounter = 0;
    @FXML
    private HBox sniperHbox;
    private ArrayList<Sniper> sniperList = new ArrayList<Sniper>();
    private int control = 0;
    @Override
    public void fuzz(String requestText) {
        ArrayList<Integer> wordCount = new ArrayList<>();
        HashMap<Integer,Integer> indexWord = new HashMap();
        int a = 0;
        for (Sniper sniperPayload:sniperList) {
            wordCount.add(sniperPayload.getTotalWordCount());
            indexWord.put(a,0);
            a++;
        }
        String _requestText = requestText;
        System.out.println("TOTAL WORD COUNT:" +getTotalWordCount());
        this.totalWordCount = getTotalWordCount();
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

                                            String requestText = _requestText;
                                            ArrayList wordList = new ArrayList<>();
                                            for (int i = 0; i < wordCount.size(); i++) {
                                                wordList.add(sniperList.get(i).getWordObservableList().get((Integer) indexWord.get(i)));
                                            }
                                            for (int i = 0; i < paramCounter; i++) {
                                                requestText = requestText.replace("FUZZ"+i,wordList.get(i).toString());
                                            }
                                            RequestModel requestModel = NetworkFunctions.stringToRequestModel(requestText, StringValues.NetworkValues.REQUEST_TYPE_INTRUDER);
                                            requestModel.setWord((String) wordList.get(0));


                                            try {
                                                NetworkFunctions.sendRequest(requestModel);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            try{
                                                int c = 0;
                                                indexWord.replace(c,(Integer)indexWord.get(c) +1);
                                                while (indexWord.get(c) >= wordCount.get(c)){
                                                    indexWord.replace(c,0);
                                                    c++;
                                                    indexWord.replace(c,indexWord.get(c) + 1);
                                                }
                                            }catch (NullPointerException e){

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

    }
    @Override
    public int getTotalWordCount() {
        int i = 1;
        for (Sniper sniperPayload:sniperList) {
           i = i * sniperPayload.getTotalWordCount();
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

