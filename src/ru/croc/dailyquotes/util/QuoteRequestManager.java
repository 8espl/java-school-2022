package ru.croc.dailyquotes.util;

import ru.croc.dailyquotes.dao.LikeDAO;
import ru.croc.dailyquotes.dao.UserDAO;
import ru.croc.dailyquotes.database.DBConnectionGetter;
import ru.croc.dailyquotes.entity.ClickAction;
import ru.croc.dailyquotes.entity.Quote;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class QuoteRequestManager implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Quote dailyQuote; // цитата дня, которую последней видел пользователь
    private boolean isLiked = false;

    public QuoteRequestManager(Socket socket) {
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            ClosingEverythingModule closingModule = new ClosingEverythingModule();
            closingModule.closeEverything(socket, reader, writer);
        }
    }

    @Override
    public void run() {
        try {
            // при запросе пользователь всегда сначала получает цитату дня
            int userId = Integer.parseInt(reader.readLine());
            String quoteToShow = processRequest(ClickAction.SHOW_QUOTE, userId);
            writer.write(quoteToShow);
            writer.newLine();
            writer.flush();

            // если пользователь не отключается, то может отправлять новые запросы
            while (socket.isConnected()) {
                ClickAction request = ClickAction.valueOf(reader.readLine());
                String result = processRequest(request, userId);
                writer.write(result);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException | SQLException e) {
            ClosingEverythingModule closingModule = new ClosingEverythingModule();
            closingModule.closeEverything(socket, reader, writer);
        }
    }

    private String processRequest(ClickAction request, int userId) throws SQLException {
        Connection connection = DBConnectionGetter.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        LikeDAO likeDAO = new LikeDAO(connection);
        switch (request) {
            case LIKE -> {
                if (!isLiked) {
                    likeDAO.addToFavorites(userId, dailyQuote.getId());
                    this.isLiked = true;
                    return "Liked!";
                } else {
                    return "You've already liked this quote.";
                }
            }
            case SHOW_QUOTE -> {
                this.dailyQuote = userDAO.getDailyQuote(userId);
                this.isLiked = false;
                return dailyQuote.toString();
            }
            case SHOW_FAVORITES -> {
                StringBuilder sb = new StringBuilder();
                userDAO.getLikedQuotes(userId)
                        .forEach(likedQuote -> sb.append(likedQuote.toString()).append("\n"));
                return sb.toString();
            }
        }
        return "";
    }
}
