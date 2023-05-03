package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Controllers.DiscoveryResultController;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import com.example.sinamekihunter.Utils.URLParseFunctions;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class RequestModel extends Thread {
    private RequestThreadModel requestThreadModel;
    private HashMap<String,Object> header_data = new HashMap<>();
    private HashMap<String,Object> request_data = new HashMap<>();
    private HashMap<String,Object> json_data = new HashMap<>();
    private String request_text;
    private String word;
    private String url;
    public boolean isJsonData = false;
    public ResponseModel responseModel;
    private String request_method;
    private String request_type;
    private OutputStream outputStream;
    private SocketModel socketModel;
    private String id;
    private byte[] raw_data;

    public RequestModel(String url,RequestThreadModel requestThreadModel,String word,String request_method){
        this.url = url;
        this.requestThreadModel = requestThreadModel;
        this.word = word;
        this.request_method = request_method;
        this.request_type = StringValues.NetworkValues.REQUEST_TYPE_DISCOVERY;
        this.id = UUID.randomUUID().toString();
    }
    public RequestModel(String url, String request_method, HashMap header_data, HashMap request_data, Boolean isJsonData,String request_text, OutputStream outputStream,byte[] raw_data){
        this.url = url;
        if (url.indexOf("http") == -1){
            //TODO BURAYI DEGISTIR
            this.url = header_data.get("Host")+url;
            System.out.println("URL:"+this.url);
        }
        this.outputStream = outputStream;
        this.request_method = request_method;
        this.header_data = header_data;
        this.request_data = request_data;
        this.isJsonData = isJsonData;
        this.request_text = request_text;
        this.request_type = StringValues.NetworkValues.REQUEST_TYPE_PROXY;
        this.id = UUID.randomUUID().toString();
        this.raw_data = raw_data;
    }
    public byte[] getRaw_data(){return this.raw_data;}
    public HashMap getHeaderData(){
        return this.header_data;
    }
    public HashMap getRequestData(){
        return this.request_data;
    }
    public String getUrl(){return this.url;}
    public ResponseModel getResponse(){
        return this.responseModel;
    }
    public String getRequest_method(){return this.request_method;}
    public String getRequestType(){return this.request_type;}
    public String getRequestText(){return this.request_text;}
    public String getUid(){return this.id;}
    public OutputStream getOutputStream(){return this.outputStream;}
    public SocketModel getSocketModel(){return this.socketModel;}
    public void setRequestData(HashMap<String, Object> body_data) {
        this.request_data = body_data;
        updateGetRequestUrl();
    }

    public void setHeaderData(HashMap<String, Object> header_data) {
        this.header_data = header_data;
    }
    public void setResponse(ResponseModel responseModel) throws IOException {

        this.responseModel = responseModel;
        if(this.request_type == StringValues.NetworkValues.REQUEST_TYPE_DISCOVERY){
            DiscoveryResultController resultController = (DiscoveryResultController) ControllersManager.getInstance().getController(StringValues.SceneNames.DISCOVERY_RESULT_SCENE);
            resultController.updateRequest(this);
        }
        else if (Objects.equals(this.request_type, StringValues.NetworkValues.REQUEST_TYPE_PROXY)){
            byte[] a = responseModel.getContent();
            this.getOutputStream().write(a);
            this.getSocketModel().finishedRequest();
            TargetModel.getInstance().checkProxyRequest(this);
        }
        System.out.println(this);
    }
    public void setSocketModel(SocketModel socketModel){
        this.socketModel = socketModel;
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
        for (Object key: request_data.keySet()) {
            System.out.println(key+"=>"+ request_data.get(key));
        }
        if (this.isJsonData){
            System.out.println("-----Json-----");
            for (Object key:json_data.keySet()) {
                System.out.println(key+"=>"+json_data.get(key));
            }
        }
    }
    private void updateGetRequestUrl(){
        if (getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_GET && getRequestData().keySet().size() != 0){
            String endpoint = url.substring(0,url.indexOf('?'));
            this.url = endpoint + URLParseFunctions.requestParamsToUrlForm(getRequestData());
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
