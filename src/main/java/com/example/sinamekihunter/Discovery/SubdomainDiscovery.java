package com.example.sinamekihunter.Discovery;

import com.example.sinamekihunter.Controllers.DiscoverySubdomainViewController;
import com.example.sinamekihunter.Models.TargetModel;

import java.io.File;

public class SubdomainDiscovery extends Discovery{

    public SubdomainDiscovery(File wordlist, int threadSpeed) {
        super(wordlist, threadSpeed);
    }

    @Override
    public void startDiscovery(){

    }
}
