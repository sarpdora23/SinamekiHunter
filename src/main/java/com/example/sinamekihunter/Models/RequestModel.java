package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Controllers.DiscoveryResult;
import com.example.sinamekihunter.Controllers.Repeater;
import com.example.sinamekihunter.Managers.ControllersManager;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.StringValues;
import com.example.sinamekihunter.Utils.URLParseFunctions;
import com.example.sinamekihunter.Vulnerabilities.ControlledVuln;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class RequestModel extends Thread {
    private RequestThreadModel requestThreadModel;
    private HashMap<String,Object> header_data = new HashMap<>();
    private HashMap<String,Object> request_data = new HashMap<>();
    private HashMap<String,Object> json_data = new HashMap<>();
    private String request_text;
    private String word;
    private String url;
    public String parameterType = StringValues.NetworkValues.PARAMETER_TYPE_BODY;
    public ResponseModel responseModel;
    private String request_method;
    private String request_type;
    private OutputStream outputStream;
    private SocketModel socketModel;
    private String id;
    private byte[] raw_data;
    private ArrayList<ControlledVuln> controlled_vulns = new ArrayList<>();
    private ArrayList<String> possible_vulns = new ArrayList<>();
    private boolean isValid = true;
    public RequestModel(){
        this.isValid = false;
    }

    public RequestModel(String url,RequestThreadModel requestThreadModel,String word,String request_method){
        this.url = url;
        this.requestThreadModel = requestThreadModel;
        this.word = word;
        this.request_method = request_method;
        this.request_type = StringValues.NetworkValues.REQUEST_TYPE_DISCOVERY;
        this.id = UUID.randomUUID().toString();
    }
    public RequestModel(String url, String request_method, HashMap header_data, HashMap request_data, String parameterType,String request_text, OutputStream outputStream,byte[] raw_data){
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
        this.parameterType = parameterType;
        this.request_text = request_text;
        this.request_type = StringValues.NetworkValues.REQUEST_TYPE_PROXY;
        this.id = UUID.randomUUID().toString();
        this.raw_data = raw_data;
    }
    public RequestModel(String url, String request_method, HashMap header_data, HashMap request_data, String parameterType,String request_text,String request_type){
        this.url = url;
        if (url.indexOf("http") == -1){
            //TODO BURAYI DEGISTIR
            this.url = header_data.get("Host")+url;
            System.out.println("URL:"+this.url);
        }
        this.request_method = request_method;
        this.header_data = header_data;
        this.request_data = request_data;
        this.parameterType = parameterType;
        this.request_text = request_text;
        this.request_type = request_type;
        this.id = UUID.randomUUID().toString();
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
    public ArrayList<ControlledVuln> getControlledVulns(){return this.controlled_vulns;}
    public HashSet<String> getPossibleVulns(){return new HashSet<>(this.possible_vulns);}
    public void addControlledVuln(ControlledVuln controlledVuln){this.controlled_vulns.add(controlledVuln);}
    public void addPossibleVuln(String possible_vuln){this.possible_vulns.add(possible_vuln);}
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
            DiscoveryResult resultController = (DiscoveryResult) ControllersManager.getInstance().getController(StringValues.SceneNames.DISCOVERY_RESULT_SCENE);
            resultController.updateRequest(this);
        }
        else if (Objects.equals(this.request_type, StringValues.NetworkValues.REQUEST_TYPE_PROXY)){
            byte[] a = responseModel.getContent();
            this.getOutputStream().write(a);
            this.getSocketModel().finishedRequest();
            TargetModel.getInstance().checkProxyRequest(this);
        }
        else if(Objects.equals(this.request_type,StringValues.NetworkValues.REQUEST_TYPE_REPEATER)){
            Repeater repeaterController = (Repeater) ControllersManager.getInstance().getController(StringValues.SceneNames.REPEATER_VIEW_SCENE);
            repeaterController.setResponse(this.responseModel.getContentString());
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
    public boolean isValid(){
        return this.isValid;
    }
    public void setValidation(boolean valid){
        this.isValid = valid;
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
    public String getParameterType(){return this.parameterType;}
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
