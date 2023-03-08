package com.codecool.elevator;

public abstract class Request {
    private final int floor;

    public Request(int floor) {
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }
}
