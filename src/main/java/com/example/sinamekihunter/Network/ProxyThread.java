package com.example.sinamekihunter.Network;

import com.example.sinamekihunter.Utils.NetworkFunctions;

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
                String request = NetworkFunctions.inputStreamToString(inputStream);
                System.out.println(request);
                NetworkFunctions.stringToRequestModel(request);
                OutputStream outputStream = socket.getOutputStream();
                String htmlTest = "<html><body><h1>Test Success</h1></body></html>";
                final String CRLF = "\n\r";
                String response = "HTTP/1.1 200 OK"+CRLF+
                        "Content-Length: " + htmlTest.getBytes().length + CRLF+
                        CRLF+
                        htmlTest+
                        CRLF+CRLF;
                outputStream.write(response.getBytes());
                inputStream.close();
                outputStream.close();
                socket.close();
            }
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
