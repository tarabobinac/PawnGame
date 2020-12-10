package com.chess.gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Client implements Serializable {

    private final Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    Client(String IP) throws IOException {
        socket = new Socket(IP, 3000);
        socket.setSoTimeout(40000);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setObjectInputStream(Socket socket) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void setObjectOutputStream(Socket socket) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void clearAll() throws IOException {
        this.inputStream.close();
        this.outputStream.close();
        this.socket.close();
    }
}