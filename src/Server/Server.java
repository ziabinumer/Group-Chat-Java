package Server;

import java.io.*;
import java.net.*;

class Server {
    private ServerSocket serverSocket;
    

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer() {
        try {
            while (!serverSocket.isClosed()) {
                serverSocket.accept();
                System.out.println("New client connected");

                // client handling
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Server server = new Server(serverSocket);
            server.runServer();
        } catch (IOException io) {
            e.printStackTrace();
        }
    }
}