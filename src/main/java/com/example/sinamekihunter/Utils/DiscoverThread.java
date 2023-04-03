package com.example.sinamekihunter.Utils;

import com.example.sinamekihunter.Managers.RequestManager;
import com.example.sinamekihunter.Models.RequestThreadModel;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class DiscoverThread extends Thread{
    RequestThreadModel newThread;
    RequestManager requestManager;
    String url;
    HashMap headerData;
    HashMap bodyData;
    HashMap jsonData;
    String fuzzParam;
    String threadName;
    public DiscoverThread(RequestThreadModel newThread,RequestManager requestManager,
                          String url,HashMap headerData,
                          HashMap bodyData,HashMap jsonData,
                          String fuzzParam,String threadName){
        this.newThread = newThread;
        this.requestManager = requestManager;
        this.url=url;
        this.headerData = headerData;
        this.bodyData = bodyData;
        this.jsonData = jsonData;
        this.fuzzParam = fuzzParam;
        this.threadName = threadName;
    }
    @Override
    public void run(){
        try {
            newThread.startThread(url,headerData,bodyData,jsonData,fuzzParam);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        requestManager.addThread(newThread,threadName);
    }
}
