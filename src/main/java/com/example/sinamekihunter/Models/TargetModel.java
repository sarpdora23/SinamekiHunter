package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Utils.URLParseFunctions;

import java.io.IOException;
import java.util.ArrayList;

public class TargetModel {
    private ArrayList<DomainModel> all_domains;
    private static TargetModel targetInstance;
    private String pure_domain;
    private String protocol;
    private boolean hasError = false;
    private TargetModel(String url){
        if (url.indexOf("http://") != 0 && url.indexOf("https://") != 0){
            hasError = true;
        }
        else{
            hasError = false;
            this.protocol = URLParseFunctions.getProtocol(url);
            String domain = URLParseFunctions.getDomainHost(url);
            all_domains = new ArrayList<>();

            pure_domain = URLParseFunctions.getPureDomain(domain);
        }
    }
    public void getReadyToListenTarget(String domain){
        DomainModel domainModel = new DomainModel(domain,0);
        all_domains.add(domainModel);
    }
    public boolean getHasError(){
        return hasError;
    }
    public static void createInstance(String url){
        targetInstance = new TargetModel(url);
    }
    public static TargetModel getInstance(){
        return  targetInstance;
    }
    public void setPureDomain(String pureDomain){
        this.pure_domain = pureDomain;
    }
    public String getPureDomain(){
        return this.pure_domain;
    }
    public String getProtocol(){
        return this.protocol;
    }
    public ArrayList<DomainModel> getDomainList(){
        return this.all_domains;
    }
    public void checkProxyRequest(RequestModel requestModel) throws IOException {
        String url = URLParseFunctions.getDomainHost(requestModel.getUrl());
        //TODO BURDA BI IF KONTROLU DOMAINMODEL CHECK YAPILSIN
        all_domains.get(0).addRequestToDomain(requestModel);
    }
}
