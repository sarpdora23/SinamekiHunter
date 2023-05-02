package com.example.sinamekihunter.Utils;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.ResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkFunctions {
    public static void sendRequest(RequestModel requestModel) throws IOException {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();
        CloseableHttpClient client = httpClientBuilder.setDefaultRequestConfig(config).build();

        if (requestModel.getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_GET){
            try{
                HttpRequest getRequest = new HttpGet(requestModel.getUrl());
                for (Object header_key_object: requestModel.getHeaderData().keySet()) {
                    String header_key = header_key_object.toString();
                    getRequest.addHeader(header_key,(String) requestModel.getHeaderData().get(header_key_object));
                }
                CloseableHttpResponse response = client.execute((HttpUriRequest) getRequest);
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),entity.getContent().readAllBytes());
                requestModel.setResponse(responseModel);

            }catch (Exception e){
                throw e;
            }
        }
        if (requestModel.getRequest_method() == StringValues.NetworkValues.REQUEST_TYPE_POST){

            try{
                HttpPost postRequest = new HttpPost(requestModel.getUrl());
                for (Object header_key_object: requestModel.getHeaderData().keySet()) {
                    String header_key = header_key_object.toString();
                    if (header_key.indexOf("Content-Length") == -1){
                        postRequest.addHeader(header_key,(String) requestModel.getHeaderData().get(header_key_object));
                    }

                }
                if (requestModel.isJsonData){
                    StringEntity stringEntity = new StringEntity(hashmapToString(requestModel.getRequestData()), ContentType.APPLICATION_JSON);
                    postRequest.setEntity(stringEntity);
                }
                else{
                    List<NameValuePair> params = new ArrayList<>();
                    for (Object body_key_object: requestModel.getRequestData().keySet()) {
                        String body_key = body_key_object.toString();
                        params.add(new BasicNameValuePair(body_key,(String) requestModel.getHeaderData().get(body_key_object)));

                    }
                    postRequest.setEntity(new UrlEncodedFormEntity(params));
                }
                CloseableHttpResponse response = client.execute((HttpUriRequest) postRequest);
                HttpEntity entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),entity.getContent().readAllBytes());
                requestModel.setResponse(responseModel);
            }catch (Exception e){
                throw e;
            }
        }
    }
    //TODO nahamstore'da sotck özelliğinde request response'da bir sıkıntı var ona bakmayı unutma
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
    public static RequestModel stringToRequestModel(String requestString,OutputStream outputStream){
        HashMap requestData = new HashMap();;
        HashMap headerParams = new HashMap<>();

        String method = "";
        String endpoint = "";
        Boolean isJson = false;

        String[] stringSplits = requestString.split("\r\n\r\n");
        String headAndInfo = stringSplits[0];
        String[] headersAndInfo = headAndInfo.split("\r\n");

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
            System.out.println("False");
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
                    isJson = false;
                    for (String param:params) {
                        String[] parameter = param.split("=");
                        if(parameter.length < 2){ requestData.put(parameter[0],"");}
                        else{requestData.put(parameter[0],parameter[1]);}
                    }
                }
                else{
                    isJson = true;
                    requestData = jsonToHashMap(body);
                }
            }
        }

        RequestModel createdRequestModel = new RequestModel(endpoint,method,headerParams,requestData,isJson,requestString,outputStream);
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
