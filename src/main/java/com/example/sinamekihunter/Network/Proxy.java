package com.example.sinamekihunter.Network;


import java.io.IOException;
import java.net.ServerSocket;

public class Proxy {
    private int port;
    private ServerSocket serverSocket;
    private static Proxy proxyInstance;

    public static void setProxyUp(int port){
        proxyInstance = new Proxy(port);
    }
    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Proxy server starting on port:" + this.port);
        ProxyThread proxyThread = new ProxyThread(this.serverSocket);
        proxyThread.start();
    }
    private Proxy(int port){
        this.port = port;
    }
    public static Proxy getInstance(){
        return proxyInstance;
    }

}
