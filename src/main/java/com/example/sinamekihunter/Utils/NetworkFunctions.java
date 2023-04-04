package com.example.sinamekihunter.Utils;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.ResponseModel;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

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
                ResponseModel responseModel = new ResponseModel(requestModel.getWord(),statusCode,HttpStatusReasons.getReasonForStatus(statusCode),EntityUtils.toString(entity));
                requestModel.setResponse(responseModel);
            }catch (Exception e){
                throw e;
            }
        }

    }
}
