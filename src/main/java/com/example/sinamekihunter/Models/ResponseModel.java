package com.example.sinamekihunter.Models;

public class ResponseModel {
    private String word;
    private int statusCode;
    private String message;
    private byte[] content;
    private int contentLength;
    public ResponseModel(String word,int statusCode,String message,byte[] content){
        this.word = word;
        this.statusCode = statusCode;
        this.message = message;
        this.content = content;
        this.contentLength = new String(content).length();
    }
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "Word:" + this.word +"   Status Code:" + this.statusCode + "     Length:" + this.contentLength;
    }

    public String getContentString() {
        return new String(content);
    }

    public byte[] getContent() {
        return content;
    }

    public String getStatusMessage() {
        return message;
    }
    public long getContentLength(){return this.contentLength;}
    public String getWord(){
        return this.word;
    }
}
