package ru.croc.task16.entity;

public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String location) {
        int temp = location.indexOf(",");
        this.latitude = Double.parseDouble(location.substring(0, temp));
        this.longitude = Double.parseDouble(location.substring(temp + 1));
    }

    public double distance(Location l) {
        return Math.sqrt((Math.pow((latitude - l.latitude), 2)) + (Math.pow((longitude - l.longitude), 2)));
    }
}

