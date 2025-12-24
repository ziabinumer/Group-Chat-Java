package ClientHandler;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String username;

    ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.username = bufferedReader.readLine();
            clientHandlers.add(this);
            // broadcastmessage
        } catch (IOException e) {
            // close all opened stuff
        }

    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void broadcastMessage(String message) {
        try {
            for (ClientHandler ch : clientHandlers) {
                if (!ch.username.equals(this.username)) {
                    ch.bufferedWriter.write(message);
                    ch.bufferedWriter.newLine();
                    ch.bufferedWriter.flush();
                }
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        } 
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage(username + " has left the chat");
    }

    public void closeEverything(Socket socket, BufferedReader bfr, BufferedWriter bfw) {
        removeClientHandler();
        try {
            if (bfr != null) {
                bfr.close();
            }
            if (bfw != null) {
                bfr.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}