package com.example.sinamekihunter.Managers;

import com.example.sinamekihunter.Models.RequestThreadModel;
import com.example.sinamekihunter.Models.TargetModel;
import com.example.sinamekihunter.Utils.DiscoverThread;
import javafx.application.Platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class RequestManager {
    private static RequestManager instance = new RequestManager();
    private HashMap<String, RequestThreadModel> runningThreads;
    private RequestManager(){
        runningThreads = new HashMap<>();
    }
    public static RequestManager getInstance(){
        return instance;
    }
    public void addThread(RequestThreadModel newThread,String threadName){
        runningThreads.put(threadName,newThread);
    }
    public RequestThreadModel getThread(String threadName){
        return runningThreads.get(threadName);
    }
    public void removeThread(String threadName){
        runningThreads.remove(threadName);
    }
    public void startThread(String url,String threadName, File wordlist,int threadSpeed,String threadMethod,HashMap headerData,HashMap bodyData,HashMap jsonData,String fuzzParam) throws FileNotFoundException, InterruptedException {
        RequestThreadModel newThread = new RequestThreadModel(threadName,wordlist,threadSpeed,threadMethod);
        DiscoverThread test = new DiscoverThread(newThread,this,url,headerData,bodyData,jsonData,fuzzParam,threadName);
        test.start();
    }
}

