package ru.croc.dailyquotes;

import ru.croc.dailyquotes.database.DBExistenceChecker;
import ru.croc.dailyquotes.database.DBImporter;
import ru.croc.dailyquotes.util.QuoteRequestManager;
import ru.croc.dailyquotes.util.QuotesUpdater;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    private final ServerSocket SERVER_SOCKET;

    public Server(ServerSocket serverSocket) {
        this.SERVER_SOCKET = serverSocket;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(2022);
            Server server = new Server(serverSocket);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            if (!DBExistenceChecker.checkExistence()) {
                String quotesSource = "src/ru/croc/dailyquotes/resources/all_quotes.json";
                String userInfoSource = "src/ru/croc/dailyquotes/resources/users.csv";
                DBImporter fileLoader = new DBImporter(quotesSource, userInfoSource);
                fileLoader.importDB();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            closeServerSocket();
        }

        System.out.println("Server is working!");

        // поток для постоянного обновления ежедневных цитат в БД
        ScheduledThreadPoolExecutor quotesUpdate = new ScheduledThreadPoolExecutor(4);
        quotesUpdate.scheduleAtFixedRate(new QuotesUpdater(), 0, 1, TimeUnit.MINUTES);

        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (!SERVER_SOCKET.isClosed()) {
                Socket socket = SERVER_SOCKET.accept();
                QuoteRequestManager quoteRequestManager = new QuoteRequestManager(socket);
                executorService.execute(quoteRequestManager);
            }
        } catch (IOException e) {
            quotesUpdate.shutdown();
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (SERVER_SOCKET != null) {
                SERVER_SOCKET.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}