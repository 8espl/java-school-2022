package ru.croc.task11;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String nickname;

    public Client(Socket socket, String nickname) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nickname = nickname;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Отправляет сообщения на сервер
     */
    public void sendMessage() {
        try {
            bufferedWriter.write(nickname);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner in = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = in.nextLine();
                bufferedWriter.write(nickname + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Отображает сообщения других пользователей
     */
    public void readMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromChat;
                while (socket.isConnected()) {
                    try {
                        messageFromChat = bufferedReader.readLine();
                        System.out.println(messageFromChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your nickname, please.");
        String nickname = in.nextLine();
        Socket socket = new Socket("127.0.0.1", 2022);
        Client client = new Client(socket, nickname);
        client.readMessage();
        client.sendMessage();
    }
}
