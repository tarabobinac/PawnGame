package com.chess.gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Serializable {

    private final ServerSocket server;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    Server() throws IOException {
        server = new ServerSocket(3000);
        server.setSoTimeout(40000);
        server.setReuseAddress(true);
    }

    public ServerSocket getServerSocket() {
        return server;
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setSocket(ServerSocket server) throws IOException {
        socket = server.accept();
    }

    public void setObjectInputStream(Socket socket) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void setObjectOutputStream(Socket socket) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void clearAll() throws IOException {
        this.outputStream.close();
        this.inputStream.close();
        this.socket.close();
        this.server.close();
    }
}