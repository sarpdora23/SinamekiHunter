package com.example.sinamekihunter.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestModel {
    private HashMap<String,Object> header_data = new HashMap<>();
    private HashMap<String,Object> body_data = new HashMap<>();
    private HashMap<String,Object> json_data = new HashMap<>();
    private String url;
    public boolean isJsonData = false;
    public ResponseModel responseModel;
    private String request_method;

    public RequestModel(String url){
        this.url = url;
    }
    public HashMap getHeaderData(){
        return this.header_data;
    }
    public HashMap getBodyData(){
        return this.body_data;
    }
    public HashMap getJsonData(){
        return this.json_data;
    }
    public String getUrl(){return this.url;}
    public void addHeader(String key,Object value){
        this.header_data.put(key,value);
    }
    public void addBody(String key,Object value){
        this.body_data.put(key,value);
    }
    public void addJson(String key,Object value){
        this.json_data.put(key,value);
    }
    public void setRequestMethod(String request_method){
        this.request_method = request_method;
    }

    @Override
    public String toString(){
        return this.url + ":" + this.responseModel.getStatusCode();
    }
}
