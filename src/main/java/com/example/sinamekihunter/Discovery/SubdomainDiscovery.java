package com.example.sinamekihunter.Discovery;

import com.example.sinamekihunter.Managers.RequestManager;
import com.example.sinamekihunter.Utils.StringValues;
import org.apache.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SubdomainDiscovery extends Discovery{

    public SubdomainDiscovery(File wordlist, int threadSpeed) {
        super(wordlist, threadSpeed);
    }
    public void startDiscovery(String url, HashMap headerData,HashMap bodyData,HashMap jsonData) throws IOException, InterruptedException {
        int threadSpeed = super.getThreadSpeed();
        File file = super.getWordlist();
        RequestManager manager = RequestManager.getInstance();
        manager.startThread(url,
                StringValues.NetworkValues.SUBDOMAIN_DISCOVERY_NAME,
                file,
                threadSpeed,
                StringValues.NetworkValues.REQUEST_TYPE_GET,
                headerData,
                bodyData,
                jsonData,
                HttpHeaders.HOST
        );

    }
}
