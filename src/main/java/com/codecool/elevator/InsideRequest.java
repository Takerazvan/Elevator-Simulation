package com.codecool.elevator;

public class InsideRequest extends Request {
    private final Direction direction;

    public InsideRequest(int floor, Direction direction) {
        super(floor);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
