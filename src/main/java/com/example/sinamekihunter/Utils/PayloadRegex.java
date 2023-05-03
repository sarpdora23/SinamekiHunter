package com.example.sinamekihunter.Utils;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.ResponseModel;

public class PayloadRegex {
    private String request_text_payload;
    private String response_text_payload;

    public PayloadRegex(String request_text_payload,String response_text_payload){
        this.request_text_payload = request_text_payload;
        this.response_text_payload = response_text_payload;
    }
    public String getRequestTextPayload(){
        return this.request_text_payload;
    }
    public String getResponseTextPayload(){
        return this.response_text_payload;
    }
    public boolean checkRequestRegex(RequestModel requestModel){
        //TODO REGEX UYUŞUYORSA TRUE DÖN UYUŞMUYORSA FALSE
        return true;
    }
    public boolean checkResponseRegex(ResponseModel responseModel){
        //TODO REGEX UYUŞUYORSA TRUE DÖN UYUŞMUYORSA FALSE
        return true;
    }
}
