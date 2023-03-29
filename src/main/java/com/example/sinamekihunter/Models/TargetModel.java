package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Utils.URLParseFunctions;

import java.util.ArrayList;

public class TargetModel {
    private ArrayList<DomainModel> all_domains;
    private static TargetModel targetInstance;
    private String protocol;
    private TargetModel(String url){
        this.protocol = URLParseFunctions.getProtocol(url);
        all_domains = new ArrayList<>();
        String domain = URLParseFunctions.getDomainHost(url);
        DomainModel domainModel = new DomainModel(domain);
        all_domains.add(domainModel);
    }
    public static void createInstance(String url){
        targetInstance = new TargetModel(url);
    }
    public static TargetModel getInstance(){
        return  targetInstance;
    }
}
