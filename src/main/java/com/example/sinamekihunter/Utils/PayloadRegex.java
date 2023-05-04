package com.example.sinamekihunter.Utils;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.ResponseModel;

public class PayloadRegex {
    private String request_text_payload;
    private String response_text_payload;
    private boolean is_crossed;

    public PayloadRegex(String request_text_payload,String response_text_payload,boolean is_crossed){
        this.request_text_payload = request_text_payload;
        this.response_text_payload = response_text_payload;
        this.is_crossed = is_crossed;
    }
    public PayloadRegex(String text_payload,boolean is_request,boolean is_crossed){
        if (is_request){
            this.request_text_payload = text_payload;
        }
        else{
            this.response_text_payload = text_payload;
        }
        this.is_crossed = is_crossed;
    }
    public boolean isIs_crossed(){return this.is_crossed;}
    public String getRequestTextPayload(){
        return this.request_text_payload;
    }
    public String getResponseTextPayload(){
        return this.response_text_payload;
    }

}
