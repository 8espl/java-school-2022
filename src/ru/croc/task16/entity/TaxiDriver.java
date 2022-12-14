package ru.croc.task16.entity;

import ru.croc.task16.ComfortClass;
import ru.croc.task16.SpecialRequest;

import java.util.EnumSet;

public class TaxiDriver {
    private String id;
    private Location location;
    private ComfortClass comfortClass;
    private EnumSet<SpecialRequest> specialRequests;

    public TaxiDriver(String id, Location location, ComfortClass comfortClass, EnumSet<SpecialRequest> specialRequests){
        this.id = id;
        this.location = location;
        this.comfortClass = comfortClass;
        this.specialRequests = specialRequests;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public ComfortClass getComfortClass() {
        return comfortClass;
    }

    public EnumSet<SpecialRequest> getSpecialRequests() {
        return specialRequests;
    }

    @Override
    public String toString() {
        return id;
    }
}
