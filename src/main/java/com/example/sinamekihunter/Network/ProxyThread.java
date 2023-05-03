package com.example.sinamekihunter.Network;

import com.example.sinamekihunter.Models.RequestModel;
import com.example.sinamekihunter.Models.ResponseModel;
import com.example.sinamekihunter.Models.SocketModel;
import com.example.sinamekihunter.Utils.NetworkFunctions;
import com.example.sinamekihunter.Utils.URLParseFunctions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyThread extends Thread{
    private ServerSocket serverSocket;
    public ProxyThread(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    @Override
    public void run(){
        try {
            while(this.serverSocket.isBound() && !this.serverSocket.isClosed()){

                Socket socket = serverSocket.accept();
                System.out.println("Request is here!!!!");
                InputStream inputStream  = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                SocketModel socketModel = new SocketModel(socket,outputStream,inputStream);
                String request = NetworkFunctions.inputStreamToString(inputStream);
                if(request.equals("")){
                    System.out.println("NULL");
                    socketModel.finishedRequest();
                }
                else{
                    RequestModel requestModel = NetworkFunctions.stringToRequestModel(request,outputStream,inputStream);
                    requestModel.setSocketModel(socketModel);
                    NetworkFunctions.sendRequest(requestModel);
                }

            }

        } catch (IOException e) {
            //throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
