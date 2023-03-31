package com.example.sinamekihunter.Models;

public class ResponseModel {
    private int statusCode;
    private String message;
    private String content;

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }
}
