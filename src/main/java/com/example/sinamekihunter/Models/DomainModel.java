package com.example.sinamekihunter.Models;

public class DomainModel {
    private String domainUrl;

    public DomainModel(String domainUrl){
        this.domainUrl = domainUrl;
    }
    public String getDomainUrl(){
        return this.domainUrl;
    }
}
