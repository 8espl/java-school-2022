package ru.croc.task19.entity;

public class Courier {
    String ID;
    private String firstName;
    private String secondName;

    public Courier(String ID, String firstName, String secondName) {
        this.ID = ID;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
}