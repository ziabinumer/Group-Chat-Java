package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String username;

    Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.username = username;
        } catch (IOException e) {

        }
    }

    public void sendMessage() {
        try {
                bufferedWriter.write(username);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                Scanner scanner = new Scanner(System.in);
                while (socket.isConnected()) {  // Change this line
                    String message = scanner.nextLine();
                    bufferedWriter.write(username + ": " + message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
    } catch (IOException e) {
            e.printStackTrace();
    }
    }

    public void listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msgFromServer;
                    while ((msgFromServer = bufferedReader.readLine()) != null) {  // Store in variable
                        System.out.println(msgFromServer);  // Print the variable
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bfr, BufferedWriter bfw) {
        try {
            if (bfr != null) {
                bfr.close();
            }
            if (bfw != null) {
                bfw.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter username for chatting: ");
        String username = scanner.nextLine();

        try {
            Socket socket = new Socket("localhost", 5000);
            Client client = new Client(socket, username);
            client.listen();
            client.sendMessage();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}