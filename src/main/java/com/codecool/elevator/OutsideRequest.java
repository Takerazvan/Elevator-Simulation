package com.codecool.elevator;

public class OutsideRequest extends Request {
    private final Direction direction;

    public OutsideRequest(int floor, Direction direction) {
        super(floor);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
