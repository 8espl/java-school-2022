package ru.croc.dailyquotes.entity;

public class Like {
    private User user;
    private Quote quote;

    public Like(User user, Quote quote) {
        this.user = user;
        this.quote = quote;
    }

    public User getUser() {
        return user;
    }

    public Quote getQuote() {
        return quote;
    }
}
