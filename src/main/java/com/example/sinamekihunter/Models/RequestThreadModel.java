package com.example.sinamekihunter.Models;

import java.util.ArrayList;

public class RequestThreadModel {
    private ArrayList<RequestModel> completedRequest;
    private String threadName;

    public RequestThreadModel(String threadName){
        this.completedRequest = new ArrayList<>();
        this.threadName = threadName;
    }
    public void addCompleteRequest(RequestModel requestModel){
        this.completedRequest.add(requestModel);
    }
    public void finishRequest(){
        for (RequestModel requestModel:completedRequest) {
            System.out.println(requestModel);
        }
    }
}
