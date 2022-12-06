package ru.croc.task16.entities;

import java.util.List;

public class User {
    private Location location;
    private String carType;
    private List<String> requests;
    public User(String location, String type, List<String> request) {
        this.location = new Location(Double.parseDouble(location.substring(0, location.indexOf(","))),
                Double.parseDouble(location.substring(location.indexOf(",") + 1)));
        this.carType = type;
        this.requests = request;
    }

    public String getCarType() {
        return carType;
    }

    public List<String> getRequests() {
        return requests;
    }

    public Location getLocation() {
        return location;
    }
}
