package ru.croc.task16.entity;

import ru.croc.task16.ComfortClass;
import ru.croc.task16.SpecialRequest;

import java.util.EnumSet;
import java.util.Set;

public class Request {
    private Location location;
    private ComfortClass comfortClass;
    private EnumSet<SpecialRequest> specialRequests;

    public Request(Location location, ComfortClass type, EnumSet<SpecialRequest> requests) {
        this.location = location;
        this.comfortClass = type;
        this.specialRequests = requests;
    }

    public ComfortClass getComfortClass() {
        return comfortClass;
    }

    public EnumSet<SpecialRequest> getSpecialRequests() {
        return specialRequests;
    }

    public Location getLocation() {
        return location;
    }
}
