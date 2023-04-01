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
    public int getThreadSpeed(){
        return this.threadSpeed;
    }
    public File getWordlist(){
        return this.wordlist;
    }

    public void stopDiscovery(){

    }
}
