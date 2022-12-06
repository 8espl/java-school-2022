package ru.croc.task16.entities;

import java.util.HashSet;

public class TaxiDriver {
    private String id;
    private Location location;
    private String carType;
    private HashSet<String> specialRequests;

    public TaxiDriver(String id, Location location, String carType, HashSet<String> specialRequests){
        this.id = id;
        this.location = location;
        this.carType = carType;
        this.specialRequests = specialRequests;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getCarType() {
        return carType;
    }

    public HashSet<String> getSpecialRequests() {
        return specialRequests;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return id;
    }
}
