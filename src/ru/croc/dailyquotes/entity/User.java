package ru.croc.dailyquotes.entity;

public class User {
    private int id;
    private int dailyQuoteId;

    public User(int id, int dailyQuoteId) {
        this.id = id;
        this.dailyQuoteId = dailyQuoteId;
    }

    public int getId() {
        return id;
    }

    public int getDailyQuoteId() {
        return dailyQuoteId;
    }
}
