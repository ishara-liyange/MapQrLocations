package com.qrmaplocations;

public class Location {
    private String name;
    private String description;
    private double distance;

    public Location(String name, String description, double distance) {
        this.name = name;
        this.description = description;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getDistance() {
        return distance;
    }
}
