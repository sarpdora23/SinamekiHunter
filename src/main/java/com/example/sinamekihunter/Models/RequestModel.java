package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Controllers.DiscoveryResultController;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestModel extends Thread {
    private RequestThreadModel requestThreadModel;
    private HashMap<String,Object> header_data = new HashMap<>();
    private HashMap<String,Object> body_data = new HashMap<>();
    private HashMap<String,Object> json_data = new HashMap<>();
    private String request_text;
    private String word;
    private String url;
    public boolean isJsonData = false;
    public ResponseModel responseModel;
    private String request_method;
    private Boolean isDiscovery = true;

    public RequestModel(String url,RequestThreadModel requestThreadModel,String word,String request_method){
        this.url = url;
        this.requestThreadModel = requestThreadModel;
        this.word = word;
        this.request_method = request_method;
        this.isDiscovery = true;
    }
    public RequestModel(String url,String request_method,HashMap header_data,HashMap body_data,Boolean isJsonData,HashMap json_data,String request_text){
        this.url = url;
        if (url.indexOf("http") == -1){
            //TODO BURAYI DEGISTIR
            this.url = "http://"+header_data.get("Host")+url;
            System.out.println("URL:"+this.url);
        }
        this.request_method = request_method;
        this.header_data = header_data;
        this.body_data = body_data;
        this.isJsonData = isJsonData;
        this.request_text = request_text;
        this.isDiscovery = false;
        if (isJsonData){
            this.json_data = json_data;
        }
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
    public ResponseModel getResponse(){
        return this.responseModel;
    }
    public String getRequest_method(){return this.request_method;}
    public Boolean IsDiscovery(){return this.isDiscovery;}
    public void setBodyData(HashMap<String, Object> body_data) {
        this.body_data = body_data;
    }

    public void setHeaderData(HashMap<String, Object> header_data) {
        this.header_data = header_data;
    }

    public void setJsonData(HashMap<String, Object> json_data) {
        this.json_data = json_data;
    }
    public void setResponse(ResponseModel responseModel,Boolean isDiscovery){
        this.responseModel = responseModel;
        if(isDiscovery){
            DiscoveryResultController resultController = (DiscoveryResultController) ControllersManager.getInstance().getController(StringValues.SceneNames.DISCOVERY_RESULT_SCENE);
            resultController.updateRequest(this);
        }
        System.out.println(this);
    }

    public void addHeader(String key, Object value){
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
    public String getWord(){
        return this.word;
    }
    public void readTest(){
        System.out.println("Method:"+this.request_method);
        System.out.println("Endpoint:"+this.url);
        System.out.println("-----Headers-----");
        for (Object key:header_data.keySet()) {
            System.out.println(key+"=>"+header_data.get(key));
        }
        System.out.println("-----Body-----");
        for (Object key:body_data.keySet()) {
            System.out.println(key+"=>"+body_data.get(key));
        }
        if (this.isJsonData){
            System.out.println("-----Json-----");
            for (Object key:json_data.keySet()) {
                System.out.println(key+"=>"+json_data.get(key));
            }
        }
    }
    @Override
    public String toString(){
        return this.url + ":" + this.responseModel.getStatusCode();
    }
    @Override
    public void run(){
        try {
            System.out.println("Header Host:" + header_data.get(HttpHeaders.HOST));
            NetworkFunctions.sendRequest(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
