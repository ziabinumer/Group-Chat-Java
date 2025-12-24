package server;

import java.io.*;
import java.net.*;
import handler.ClientHandler;

class Server {
    private ServerSocket serverSocket;
    

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // client handling
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}