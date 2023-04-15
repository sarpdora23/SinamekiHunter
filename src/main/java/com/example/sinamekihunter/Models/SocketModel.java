package com.example.sinamekihunter.Models;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketModel {
    public Socket socket;
    public OutputStream outputStream;
    public InputStream inputStream;

    public SocketModel(Socket socket, OutputStream outputStream, InputStream inputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }
    public void finishedRequest() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
