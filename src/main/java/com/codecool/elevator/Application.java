package com.codecool.elevator;


public class Application {

    public static void main(String[] args) {

        // Create elevator
        int numFloors = 5;
        Elevator elevator = new Elevator(1, numFloors);

        // Add elevator as observer
        ControlPanel controlPanel = new ControlPanel(numFloors);
        controlPanel.addObserver(elevator);

        // Create incoming requests
        // Create incoming requests
//        controlPanel.addRequest(new OutsideRequest(0, Direction.UP));
//        controlPanel.addRequest(new InsideRequest(3, Direction.UP));
//        controlPanel.addRequest(new InsideRequest(2, Direction.UP));
//        controlPanel.addRequest(new OutsideRequest(4, Direction.DOWN));
//        controlPanel.addRequest(new OutsideRequest(1, Direction.DOWN));
//        controlPanel.addRequest(new InsideRequest(1, Direction.DOWN));
//        controlPanel.addRequest(new InsideRequest(2, Direction.DOWN));
//        controlPanel.addRequest(new InsideRequest(0, Direction.DOWN));
        // Start elevator
        while (controlPanel.hasRequests()) {
            elevator.move();
        }
    }
}
