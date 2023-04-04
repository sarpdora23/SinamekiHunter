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
    private String word;
    private String url;
    public boolean isJsonData = false;
    public ResponseModel responseModel;
    private String request_method;

    public RequestModel(String url,RequestThreadModel requestThreadModel,String word,String request_method){
        this.url = url;
        this.requestThreadModel = requestThreadModel;
        this.word = word;
        this.request_method = request_method;
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

    public void setBodyData(HashMap<String, Object> body_data) {
        this.body_data = body_data;
    }

    public void setHeaderData(HashMap<String, Object> header_data) {
        this.header_data = header_data;
    }

    public void setJsonData(HashMap<String, Object> json_data) {
        this.json_data = json_data;
    }
    public void setResponse(ResponseModel responseModel){
        this.responseModel = responseModel;
        DiscoveryResultController resultController = (DiscoveryResultController) ControllersManager.getInstance().getController(StringValues.SceneNames.DISCOVERY_RESULT_SCENE);
        resultController.updateRequest(this);
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
