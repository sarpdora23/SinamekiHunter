package com.example.sinamekihunter.Models;


import com.example.sinamekihunter.Utils.StringValues;

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
    public int getCompletedRequestCounter(){
        return completedRequest.size();
    }
    public ArrayList getCompleteRequests(){
        return this.getCompleteRequests();
    }
    public String getThreadName(){
        return this.threadName;
    }
    public File getWordlist(){
        return this.wordlist;
    }
    public void startThread(String url, HashMap headerData,HashMap bodyData,HashMap jsonData,String fuzzParam,String requestMethod) throws FileNotFoundException, InterruptedException {
        String fuzzParamType = "";
        for (Object header : headerData.keySet()) {
            if (headerData.get(header).toString().indexOf(StringValues.NetworkValues.FUZZ_PARAM_VALUE) != -1) {
                fuzzParamType = "HEADER";
                fuzzParam = (String) header;
                break;
            }
        }
        for (Object body : bodyData.keySet()) {
            if (bodyData.get(body).toString().indexOf(StringValues.NetworkValues.FUZZ_PARAM_VALUE) != -1) {
                fuzzParamType = "BODY";
                fuzzParam = (String) body;
                break;
            }
        }
        for (Object json : jsonData.keySet()) {
            if (jsonData.get(json).toString().indexOf(StringValues.NetworkValues.FUZZ_PARAM_VALUE) != -1) {
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

                HashMap headerDataCopy = (HashMap) headerData.clone();
                HashMap bodyDataCopy = (HashMap) bodyData.clone();
                HashMap jsonDataCopy = (HashMap) bodyData.clone();

                RequestModel requestModel = new RequestModel(url,this,word,requestMethod);
                requestModel.setRequestMethod(this.threadMethod);
                requestModel.setHeaderData(headerDataCopy);
                requestModel.setRequestData(bodyDataCopy);

                switch (fuzzParamType) {
                    case "HEADER":
                        String value = (String) requestModel.getHeaderData().get(fuzzParam);
                        int replaceIndex = value.indexOf(StringValues.NetworkValues.FUZZ_PARAM_VALUE);
                        value = word + value.substring(replaceIndex + 4);
                        requestModel.getHeaderData().put(fuzzParam, value);
                        break;
                    case "BODY":
                        value = (String) requestModel.getRequestData().get(fuzzParam);
                        replaceIndex = value.indexOf(StringValues.NetworkValues.FUZZ_PARAM_VALUE);
                        value = word + value.substring(replaceIndex + 4);
                        requestModel.getRequestData().put(fuzzParam, value);
                        break;
                    case "JSON":
                        value = (String) requestModel.getRequestData().get(fuzzParam);
                        replaceIndex = value.indexOf(StringValues.NetworkValues.FUZZ_PARAM_VALUE);
                        value = word + value.substring(replaceIndex + 4);
                        requestModel.getRequestData().put(fuzzParam, value);
                        break;
                    default:
                        break;
                }
                Thread thread = new Thread(requestModel::start);
                threads.add(thread);
                counter++;
            }
            for (Thread thread : threads) {
                thread.start();
            }
            Thread.sleep(100);
        }
    }
}
