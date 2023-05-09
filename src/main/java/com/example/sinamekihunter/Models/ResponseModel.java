package com.example.sinamekihunter.Models;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseModel {
    private String word;
    private int statusCode;
    private String message;
    private byte[] content;
    private int contentLength;
    private RequestModel requestModel;
    private CloseableHttpResponse raw_response;
    public ResponseModel(String word, int statusCode, String message, byte[] content, RequestModel requestModel, CloseableHttpResponse raw_response){
        this.word = word;
        this.statusCode = statusCode;
        this.message = message;
        this.content = content;
        this.contentLength = new String(content).length();
        this.requestModel = requestModel;
        this.raw_response = raw_response;
    }
    public ResponseModel(){
        this.content = "Request is not valid.".getBytes(StandardCharsets.UTF_8);
    }
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "Word:" + this.word +"   Status Code:" + this.statusCode + "     Length:" + this.contentLength;
    }

    public String getContentString() {
        try{
            return new String(getContent());
        }catch (IOException exception){

        }
        return "";
    }
    public RequestModel getRequestModel(){return this.requestModel;}
    public byte[] getContent() throws IOException {
        String content_string = "";
        content_string  = content_string +raw_response.getProtocolVersion()+" "+
                raw_response.getStatusLine().getStatusCode() + " " + raw_response.getStatusLine().getReasonPhrase()+"\r\n";
        Header[] all_headers = this.raw_response.getAllHeaders();
        for (Header header:all_headers) {
            if (!header.getName().equals("Transfer-Encoding")){
                content_string = content_string + header.getName() + ": " + header.getValue() + "\r\n";
            }
        }
        content_string = content_string + "Content-Length" + ": " + getContentLength() + "\r\n";
        content_string = content_string + "\n";
        byte[] content_header = content_string.getBytes();
        byte[] result = new byte[content_header.length + content.length];
        System.arraycopy(content_header, 0, result, 0, content_header.length);
        System.arraycopy(content, 0, result, content_header.length, content.length);
        return result;
    }

    public String getStatusMessage() {
        return message;
    }
    public long getContentLength(){return this.contentLength;}
    public String getWord(){
        return this.word;
    }
}
