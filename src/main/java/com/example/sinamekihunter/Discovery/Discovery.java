package com.example.sinamekihunter.Discovery;

import com.example.sinamekihunter.Models.TargetModel;

import java.io.File;

public abstract class Discovery {
    private TargetModel targetModel;
    private File wordlist;
    private int threadSpeed;

    public Discovery(File wordlist,int threadSpeed){
        this.wordlist = wordlist;
        this.threadSpeed = threadSpeed;
        targetModel = TargetModel.getInstance();
    }
    public void startDiscovery(){

    }
    public void stopDiscovery(){

    }
}
