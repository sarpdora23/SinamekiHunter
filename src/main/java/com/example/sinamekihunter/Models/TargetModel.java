package com.example.sinamekihunter.Models;

import com.example.sinamekihunter.Utils.URLParseFunctions;

import java.util.ArrayList;

public class TargetModel {
    private ArrayList<DomainModel> all_domains;
    private static TargetModel targetInstance;
    private String pure_domain;
    private String protocol;
    private TargetModel(String url){
        this.protocol = URLParseFunctions.getProtocol(url);
        String domain = URLParseFunctions.getDomainHost(url);
        all_domains = new ArrayList<>();
        DomainModel domainModel = new DomainModel(domain);
        all_domains.add(domainModel);
        pure_domain = URLParseFunctions.getPureDomain(domain);
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
    public static DomainModel urlToDomain(String url){
        String domain = URLParseFunctions.getDomainHost(url);
        System.out.println("Domain: " + domain);
        DomainModel domainModel = new DomainModel(domain);
        TargetModel.getInstance().all_domains.add(domainModel);
        return domainModel;
    }
}
