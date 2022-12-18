package ru.croc.dailyquotes;

import ru.croc.dailyquotes.entity.ClickAction;
import ru.croc.dailyquotes.util.ClosingEverythingModule;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RequestListener {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private int userId;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 2022);
            RequestListener requestListener = new RequestListener(socket);
            requestListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RequestListener(Socket socket) {
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            ClosingEverythingModule closingModule = new ClosingEverythingModule();
            closingModule.closeEverything(socket, reader, writer);
        }
    }

    public void start() {
        try {
            try (Scanner in = new Scanner(System.in)) {
                this.userId = Integer.parseInt(in.nextLine());
                writer.write(Integer.toString(userId));
                writer.newLine();
                writer.flush();
                String dailyQuote = reader.readLine();
                System.out.println(dailyQuote);

                // если введенное действие отсутствует среди возможных, то отправляем сообщение об ошибке
                while (socket.isConnected()) {
                    try {
                        ClickAction action = ClickAction.valueOf(in.nextLine().toUpperCase());
                        writer.write(action.toString());
                        writer.newLine();
                        writer.flush();
                        String result = reader.readLine();
                        System.out.println(result);
                    } catch (IllegalArgumentException noSuchAction) {
                        System.out.println("No such possible action.");
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            // если неправильно введен id, завершаем сеанс
            ClosingEverythingModule closingModule = new ClosingEverythingModule();
            closingModule.closeEverything(socket, reader, writer);
        }
    }
}
