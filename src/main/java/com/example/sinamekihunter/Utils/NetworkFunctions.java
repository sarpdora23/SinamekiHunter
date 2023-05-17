package com.example.sinamekihunter.Utils;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class NetworkFunctions {
    public static void sendRequest(RequestModel requestModel) throws IOException {
        if (requestModel.isValid()){
            HttpClientBuilder httpClientBuilder;
            RequestConfig config;
            if(requestModel.getRequestType() == StringValues.NetworkValues.REQUEST_TYPE_PROXY){
                httpClientBuilder = HttpClients.custom().addInterceptorFirst(
                        new HttpRequestInterceptor() {
                            @Override
                            public void process(HttpRequest request, HttpContext httpContext) throws HttpException, IOException {
                                request.addHeader("Accept-Encoding", "identity");
                            }
                        }
                );
                config = RequestConfig.custom().setRedirectsEnabled(false).build();
            }
            else{
                httpClientBuilder = HttpClients.custom().addInterceptorFirst(
                        new HttpRequestInterceptor() {
                            @Override
                            public void process(HttpRequest request, HttpContext httpContext) throws HttpException, IOException {
                                request.addHeader("Accept-Encoding", "identity");
                            }
                        }
                );
                config = RequestConfig.custom().setRedirectsEnabled(false).build();
            }
            CloseableHttpClient client = httpClientBuilder.setDefaultRequestConfig(config).build();
            if (requestModel.getRequest_method().equals(StringValues.NetworkValues.REQUEST_TYPE_GET)){
                HttpRequest getRequest = new HttpGet(requestModel.getUrl());
                for (Object header_key_object: requestModel.getHeaderData().keySet()) {
                    String header_key = header_key_object.toString();
                    getRequest.addHeader(header_key,(String) requestModel.getHeaderData().get(header_key_object));
                }
                CloseableHttpResponse response = client.execute((HttpUriRequest) getRequest);
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),entity.getContent().readAllBytes(),requestModel,response);
                requestModel.setResponse(responseModel);
            }
            else if (requestModel.getRequest_method().equals(StringValues.NetworkValues.REQUEST_TYPE_POST)){
                HttpPost postRequest = new HttpPost(requestModel.getUrl());
                for (Object header_key_object: requestModel.getHeaderData().keySet()) {
                    String header_key = header_key_object.toString();
                    if (header_key.indexOf("Content-Length") == -1){
                        postRequest.addHeader(header_key,(String) requestModel.getHeaderData().get(header_key_object));
                    }

                }
                if (requestModel.getParameterType().equals(StringValues.NetworkValues.PARAMETER_TYPE_JSON)){
                    StringEntity stringEntity = new StringEntity(hashmapToString(requestModel.getRequestData()), ContentType.APPLICATION_JSON);
                    postRequest.setEntity(stringEntity);
                }
                else{
                    String body = "";

                    for (Object body_key_object: requestModel.getRequestData().keySet()) {
                        String body_key = body_key_object.toString();
                        body = body + body_key + "="+requestModel.getRequestData().get(body_key) + "&";
                    }
                    if(body.length() != 0){
                        body = body.substring(0,body.length() -1);
                    }
                    postRequest.setEntity(new StringEntity(body,StandardCharsets.UTF_8));
                }
                CloseableHttpResponse response = client.execute((HttpUriRequest) postRequest);
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),entity.getContent().readAllBytes(),requestModel,response);
                requestModel.setResponse(responseModel);
            }
            else if (requestModel.getRequest_method().equals(StringValues.NetworkValues.REQUEST_TYPE_PUT)){
                HttpPut putRequest = new HttpPut(requestModel.getUrl());
                for (Object header_key_object: requestModel.getHeaderData().keySet()) {
                    String header_key = header_key_object.toString();
                    if (header_key.indexOf("Content-Length") == -1){
                        putRequest.addHeader(header_key,(String) requestModel.getHeaderData().get(header_key_object));
                    }

                }
                if (requestModel.getParameterType().equals(StringValues.NetworkValues.PARAMETER_TYPE_JSON)){
                    StringEntity stringEntity = new StringEntity(hashmapToString(requestModel.getRequestData()), ContentType.APPLICATION_JSON);
                    putRequest.setEntity(stringEntity);
                }
                else if (requestModel.getRequestType().equals(StringValues.NetworkValues.PARAMETER_TYPE_XML)){
                    //TODO TÜM BODY ŞEYSİLERİ NEDEN HASHMAP YAPIYORSUN DİREKT STRİNG YAP YOLLA BOŞA CONVERTLEME
                    StringEntity stringEntity = new StringEntity(requestModel.getXml_data(),ContentType.APPLICATION_ATOM_XML);
                    putRequest.setEntity(stringEntity);
                }
                else{
                    String body = "";

                    for (Object body_key_object: requestModel.getRequestData().keySet()) {
                        String body_key = body_key_object.toString();
                        body = body + body_key + "="+requestModel.getRequestData().get(body_key) + "&";
                    }
                    if(body.length() != 0){
                        body = body.substring(0,body.length() -1);
                    }
                    putRequest.setEntity(new StringEntity(body,StandardCharsets.UTF_8));
                }
                CloseableHttpResponse response = client.execute((HttpUriRequest) putRequest);
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),entity.getContent().readAllBytes(),requestModel,response);
                requestModel.setResponse(responseModel);
            }
            else if (requestModel.getRequest_method().equals(StringValues.NetworkValues.REQUEST_TYPE_OPTIONS)){
                HttpOptions optionsRequest = new HttpOptions(requestModel.getUrl());
                for (Object header_key_object: requestModel.getHeaderData().keySet()) {
                    String header_key = header_key_object.toString();
                    optionsRequest.addHeader(header_key,(String) requestModel.getHeaderData().get(header_key_object));
                }
                CloseableHttpResponse response = client.execute((HttpUriRequest) optionsRequest);
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),entity.getContent().readAllBytes(),requestModel,response);
                requestModel.setResponse(responseModel);
            }
            else if(requestModel.getRequest_method().equals(StringValues.NetworkValues.REQUEST_TYPE_HEAD)){
                HttpHead headRequest = new HttpHead(requestModel.getUrl());
                for (Object header_key_object: requestModel.getHeaderData().keySet()) {
                    String header_key = header_key_object.toString();
                    headRequest.addHeader(header_key,(String) requestModel.getHeaderData().get(header_key_object));
                }
                CloseableHttpResponse response = client.execute((HttpUriRequest) headRequest);
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                byte[] a = "".getBytes();
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),a,requestModel,response);
                requestModel.setResponse(responseModel);
            }
            else if (requestModel.getRequest_method().equals(StringValues.NetworkValues.REQUEST_TYPE_DELETE)){
                HttpDelete deleteRequest = new HttpDelete(requestModel.getUrl());
                for (Object header_key_object: requestModel.getHeaderData().keySet()) {
                    String header_key = header_key_object.toString();
                    deleteRequest.addHeader(header_key,(String) requestModel.getHeaderData().get(header_key_object));
                }
                CloseableHttpResponse response = client.execute((HttpUriRequest) deleteRequest);
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),entity.getContent().readAllBytes(),requestModel,response);
                requestModel.setResponse(responseModel);
            }
        }
        else{
            ResponseModel notValidResponse = new ResponseModel();
            requestModel.setResponse(notValidResponse);
        }
    }
    
    public static String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while (inputStream.available() > 0 && (length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.US_ASCII);
    }
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while (inputStream.available() > 0 && (length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toByteArray();
    }
    public static RequestModel stringToRequestModel(String requestString,OutputStream outputStream,InputStream inputStream) throws IOException {
        HashMap requestData = new HashMap();;
        HashMap headerParams = new HashMap<>();

        String method = "";
        String endpoint = "";
        String parameter_type = StringValues.NetworkValues.PARAMETER_TYPE_BODY;
        String xmlData = "";
        String[] stringSplits = requestString.split("\r\n\r\n");
        String headAndInfo = stringSplits[0];
        String[] headersAndInfo = headAndInfo.split("\r\n");

        String info = headersAndInfo[0];
        method = info.split(" ")[0];
        endpoint = info.split(" ")[1];
        switch (method){
            case "GET":
                method = StringValues.NetworkValues.REQUEST_TYPE_GET;
                break;
            case "POST":
                method = StringValues.NetworkValues.REQUEST_TYPE_POST;
                break;
            case "PUT":
                method = StringValues.NetworkValues.REQUEST_TYPE_PUT;
                break;
            case "OPTIONS":
                method = StringValues.NetworkValues.REQUEST_TYPE_OPTIONS;
                break;
            case "HEAD":
                method = StringValues.NetworkValues.REQUEST_TYPE_HEAD;
                break;
            case "DELETE":
                method = StringValues.NetworkValues.REQUEST_TYPE_DELETE;
                break;
        }
        for (int i = 1; i < headersAndInfo.length; i++) {
            String row = headersAndInfo[i];
            headerParams.put(row.split(": ")[0],row.substring(row.indexOf(": ")+2));
        }
        if(method == StringValues.NetworkValues.REQUEST_TYPE_GET){
            requestData = URLParseFunctions.getGetRequestParams(endpoint);

        }
        else{
            if (stringSplits.length > 1){
                String body = stringSplits[1];
                if(body.indexOf('{') == -1 && body.indexOf('}') == -1){
                    String[] params = body.split("&");
                    parameter_type = StringValues.NetworkValues.PARAMETER_TYPE_BODY;
                    for (String param:params) {
                        String[] parameter = param.split("=");
                        if(parameter.length < 2){ requestData.put(parameter[0],"");}
                        else{requestData.put(parameter[0],parameter[1]);}
                    }
                } else if (body.indexOf('<') != -1 && body.indexOf('>') != -1) {
                    xmlData = body;
                    parameter_type = StringValues.NetworkValues.PARAMETER_TYPE_XML;
                } else{
                    parameter_type = StringValues.NetworkValues.PARAMETER_TYPE_BODY;
                    requestData = jsonToHashMap(body);
                }
            }
        }

        RequestModel createdRequestModel = new RequestModel(endpoint,method,headerParams,requestData,parameter_type,requestString,outputStream,inputStreamToByteArray(inputStream));
        createdRequestModel.setXml_data(xmlData);
        return createdRequestModel;
    }
    public static String requestModelToString(RequestModel requestModel){
        String header_data = "";
        String body_data = "";
        for (String header: (Set<String>)requestModel.getHeaderData().keySet()) {
            header_data = header_data + header + ":" + requestModel.getHeaderData().get(header) + "\n";
        }
        if (requestModel.getRequest_method() != StringValues.NetworkValues.REQUEST_TYPE_GET){
            if (requestModel.getRequestData().keySet().size() != 0){
                if (!requestModel.getParameterType().equals(StringValues.NetworkValues.PARAMETER_TYPE_JSON)){
                    for (String key:(Set<String>)requestModel.getRequestData().keySet()) {
                        body_data = body_data + key + "=" + requestModel.getRequestData().get(key);
                        body_data = body_data + "&";
                    }
                    body_data = body_data.substring(0,body_data.length() -1);
                }
            }
        }
        String request_content = String.format("%s %s HTTP/1.1\n%s\n%s",
                requestModel.getRequest_method(),
                requestModel.getUrl(),
                header_data,body_data);
        return request_content;
    }
    public static RequestModel stringToRequestModel(String requestString,String request_type){
        boolean isValid = true;
        HashMap requestData = new HashMap();;
        HashMap headerParams = new HashMap<>();

        String method = "";
        String endpoint = "";
        String parameter_type = StringValues.NetworkValues.PARAMETER_TYPE_BODY;

        try{
            String[] stringSplits = requestString.split("\n\n");
            String headAndInfo = stringSplits[0];
            String[] headersAndInfo = headAndInfo.split("\n");

            String info = headersAndInfo[0];
            method = info.split(" ")[0];
            endpoint = info.split(" ")[1];
            if (method.indexOf("POST") != -1){
                method = StringValues.NetworkValues.REQUEST_TYPE_POST;
            }
            else if(method.indexOf("GET") != -1){
                method = StringValues.NetworkValues.REQUEST_TYPE_GET;
            }
            else{

            }
            for (int i = 1; i < headersAndInfo.length; i++) {
                String row = headersAndInfo[i];
                headerParams.put(row.split(": ")[0],row.substring(row.indexOf(": ")+2));

            }
            if(method == StringValues.NetworkValues.REQUEST_TYPE_GET){
                requestData = URLParseFunctions.getGetRequestParams(endpoint);
            }
            else{
                if (stringSplits.length > 1){

                    String body = stringSplits[1];

                    if(body.indexOf('{') == -1 && body.indexOf('}') == -1){
                        String[] params = body.split("&");
                        parameter_type = StringValues.NetworkValues.PARAMETER_TYPE_BODY;
                        for (String param:params) {
                            String[] parameter = param.split("=");
                            if(parameter.length < 2){ requestData.put(parameter[0],"");}
                            else{requestData.put(parameter[0],parameter[1]);}
                        }
                    }
                    else{
                        parameter_type = StringValues.NetworkValues.PARAMETER_TYPE_JSON;
                        requestData = jsonToHashMap(body);
                    }
                }
            }
        }catch (Exception e){
            isValid = false;
        }
        RequestModel createdRequestModel;
        if (isValid){
            createdRequestModel = new RequestModel(endpoint,method,headerParams,requestData,parameter_type,requestString,request_type);
        }
        else{
            createdRequestModel = new RequestModel();
        }

        return createdRequestModel;
    }
    public static String hashmapToString(HashMap hashMap){
        Gson gson = new Gson();
        String jsonString = gson.toJson(hashMap);
        return jsonString;
    }
    public static HashMap jsonToHashMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap>(){}.getType();
        HashMap<String, Object> hashMap = gson.fromJson(json, type);
        return hashMap;
    }

}
