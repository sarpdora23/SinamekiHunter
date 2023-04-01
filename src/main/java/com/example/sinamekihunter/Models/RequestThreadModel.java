package com.example.sinamekihunter.Models;


import com.example.sinamekihunter.Utils.StringValues;
import javafx.application.Platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RequestThreadModel {
    private ArrayList<RequestModel> completedRequest;
    private String threadName;
    private File wordlist;
    private int threadSpeed;
    private String threadMethod;
    public RequestThreadModel(String threadName,File wordlist,int threadSpeed,String threadMethod){
        this.completedRequest = new ArrayList<>();
        this.threadName = threadName;
        this.wordlist = wordlist;
        this.threadSpeed = threadSpeed;
        this.threadMethod = threadMethod;
    }
    public void addCompleteRequest(RequestModel requestModel){
        this.completedRequest.add(requestModel);
    }
    public void finishRequest(){
        for (RequestModel requestModel:completedRequest) {
            System.out.println(requestModel);
        }
    }
    public void startThread(String url, HashMap headerData,HashMap bodyData,HashMap jsonData,String fuzzParam) throws FileNotFoundException, InterruptedException {
        String fuzzParamType = "";
        for (Object header : headerData.keySet()) {
            if (headerData.get(header) == StringValues.NetworkValues.FUZZ_PARAM_VALUE) {
                fuzzParamType = "HEADER";
                fuzzParam = (String) header;
                break;
            }
        }
        for (Object body : bodyData.keySet()) {
            if (bodyData.get(body) == StringValues.NetworkValues.FUZZ_PARAM_VALUE) {
                fuzzParamType = "BODY";
                fuzzParam = (String) body;
                break;
            }
        }
        for (Object json : jsonData.keySet()) {
            if (jsonData.get(json) == StringValues.NetworkValues.FUZZ_PARAM_VALUE) {
                fuzzParamType = "JSON";
                fuzzParam = (String) json;
                break;
            }
        }
        Scanner scanner = new Scanner(wordlist);
        while (scanner.hasNext()) {
            int counter = 0;
            ArrayList<Thread> threads = new ArrayList<>();
            while (scanner.hasNext() && counter < threadSpeed) {
                String word = scanner.nextLine();
                System.out.println(word);

                HashMap headerDataCopy = (HashMap) headerData.clone();
                HashMap bodyDataCopy = (HashMap) bodyData.clone();
                HashMap jsonDataCopy = (HashMap) bodyData.clone();

                RequestModel requestModel = new RequestModel(url);
                requestModel.setRequestMethod(this.threadMethod);
                requestModel.setHeaderData(headerDataCopy);
                requestModel.setBodyData(bodyDataCopy);
                requestModel.setJsonData(jsonDataCopy);

                switch (fuzzParamType) {
                    case "HEADER":
                        requestModel.getHeaderData().put(fuzzParam, word);
                        break;
                    case "BODY":
                        requestModel.getBodyData().replace(fuzzParam, word);
                        break;
                    case "JSON":
                        requestModel.getJsonData().replace(fuzzParam, word);
                        break;
                    default:
                        break;
                }
                Thread thread = new Thread(() -> requestModel.start());
                threads.add(thread);
                counter++;
            }

            for (Thread thread : threads) {
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            Thread.sleep(2000);
        }
    }
}
/*
String fuzzParamType = "";
        for (Object header: headerData.keySet()) {
            if (headerData.get(header) == StringValues.NetworkValues.FUZZ_PARAM_VALUE){
                fuzzParamType = "HEADER";
                fuzzParam = (String) header;
                break;
            }
        }
        for (Object body: bodyData.keySet()) {
            if (bodyData.get(body) == StringValues.NetworkValues.FUZZ_PARAM_VALUE){
                fuzzParamType = "BODY";
                fuzzParam = (String) body;
                break;
            }
        }
        for (Object json: jsonData.keySet()) {
            if (jsonData.get(json) == StringValues.NetworkValues.FUZZ_PARAM_VALUE){
                fuzzParamType = "JSON";
                fuzzParam = (String) json;
                break;
            }
        }
        Scanner scanner = new Scanner(wordlist);
        while (scanner.hasNext()){
            int counter = 0;
            ArrayList<String> words = new ArrayList<>();
            ArrayList<Thread> threads = new ArrayList<>();
            while (scanner.hasNext() && counter < threadSpeed){
                String word = scanner.nextLine();
                words.add(word);
                counter++;
                RequestModel requestModel = new RequestModel(url);
                requestModel.setRequestMethod(this.threadMethod);
                requestModel.setHeaderData(headerData);
                requestModel.setBodyData(bodyData);
                requestModel.setJsonData(jsonData);

                switch (fuzzParamType){
                    case "HEADER":
                        requestModel.getHeaderData().put(fuzzParam,word);
                        break;
                    case "BODY":
                        requestModel.getBodyData().replace(fuzzParam,word);
                        break;
                    case "JSON":
                        requestModel.getJsonData().replace(fuzzParam,word);
                        break;
                    default:
                        break;
                }
                Thread thread = new Thread(()->requestModel.start());
                threads.add(thread);
            }

            for (Thread th:threads) {
                th.start();
            }
            for (Thread th:threads){
                th.join();
            }
            Thread.sleep(2000);
        }
 */

/*

 */