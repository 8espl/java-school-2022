package ru.croc.task11;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection implements Runnable {
    public static ArrayList<ClientConnection> clientConnections = new ArrayList<>(); // список всех поделюченных пользователей
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientNickname;

    public ClientConnection(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientNickname = bufferedReader.readLine();
            clientConnections.add(this);
            showMessageToEveryone("SERVER: " + clientNickname + " has entered the chat!");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                showMessageToEveryone(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }

    }

    public void showMessageToEveryone(String sentMessage) {
        for (ClientConnection clientConnection : clientConnections) {
            try {
                if (clientConnection != this) {
                    clientConnection.bufferedWriter.write(sentMessage);
                    clientConnection.bufferedWriter.newLine();
                    clientConnection.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void disconnectClient() {
        clientConnections.remove(this);
        showMessageToEveryone("SERVER: " + clientNickname + " has left the chat :(");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        disconnectClient();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
