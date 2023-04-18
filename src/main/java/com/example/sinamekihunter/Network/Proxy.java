package com.example.sinamekihunter.Network;


import java.io.IOException;
import java.net.ServerSocket;

public class Proxy {
    private int port;
    private ServerSocket serverSocket;
    private static Proxy proxyInstance;
    private boolean _isRunning = false;

    public static void setProxyUp(int port){
        proxyInstance = new Proxy(port);
    }
    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Proxy server starting on port:" + this.port);
        ProxyThread proxyThread = new ProxyThread(this.serverSocket);
        proxyThread.start();
        _isRunning = true;
    }
    public boolean isRunning(){
        return _isRunning;
    }
    public void stopProxy() throws IOException {
        serverSocket.close();
    }
    public ServerSocket getServerSocket(){
        return this.serverSocket;
    }
    private Proxy(int port){
        this.port = port;
    }
    public static Proxy getInstance(){
        return proxyInstance;
    }

}
