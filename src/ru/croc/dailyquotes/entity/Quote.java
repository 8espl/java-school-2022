package ru.croc.dailyquotes.entity;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private int id;
    @JsonProperty("quote")
    private String text;
    private String author;
    private Category category;

    public Quote() {
    }

    public Quote(int id, String text, String author, Category category) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.category = category;
    }

    public int getID() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return text + " " + author;
    }
}
