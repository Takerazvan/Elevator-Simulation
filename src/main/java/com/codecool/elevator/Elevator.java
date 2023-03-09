package com.codecool.elevator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
public class Elevator implements Observer {
    private final int id;
    private long lastRequestTime;
    private int currentFloor;
    private Direction direction;
    private List<Integer> requests;
    private final int maxFloors;
    private int currentLoading;
    private static final int MAX_LOADING = 10; // Maximum number of passengers that the elevator can hold

    public Elevator(int id, int maxFloors) {
        this.id = id;

        this.currentFloor = 0;
        this.direction = Direction.STATIONARY;
        this.requests = new ArrayList<>();
        this.maxFloors = maxFloors;
        this.lastRequestTime = System.currentTimeMillis();

    }

    public int getId() {
        return id;
    }

    public boolean addPassenger() {
        if (currentLoading < MAX_LOADING) {
            currentLoading++;
        }
        return false;
    }


    public void removePassenger() {
        if (currentLoading > 0) {
            currentLoading--;
        }
    }

    public int getCurrentLoading() {
        return currentLoading;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Integer> getRequests() {
        return Collections.unmodifiableList(requests);
    }



    public void addRequest(int floor) {
        if (floor >= 0 && floor < maxFloors && !requests.contains(floor)) {
            requests.add(floor);
            Collections.sort(requests);
        }
    }

    public void move() {


        if (requests.isEmpty()) {
            long currentTime = System.currentTimeMillis();
            long timeSinceLastRequest = currentTime - lastRequestTime;
            direction=Direction.STATIONARY;
            if (timeSinceLastRequest >= 18000 && currentFloor > 0) {
                System.out.printf("Elevator %d has no more requests for 20 seconds. Returning to ground floor.%n", id);

                addRequest(0);

            }
            return;
        }

        int nextFloor = getNextFloor();

        if (nextFloor == currentFloor) {

            requests.remove((Integer) nextFloor);
            System.out.printf("Elevator %d stopped at floor %d.%n", id, currentFloor);

            return;
        }

        if (nextFloor > currentFloor) {
            System.out.printf("Elevator %d going UP from floor %d to floor %d.%n", id, currentFloor, nextFloor);
            direction = Direction.UP;
        } else if(nextFloor<currentFloor) {
            System.out.printf("Elevator %d going DOWN from floor %d to floor %d.%n", id, currentFloor, nextFloor);
            direction = Direction.DOWN;
        } else {
            direction=Direction.STATIONARY;
        }

        currentFloor = nextFloor;
        requests.remove((Integer) nextFloor);

        System.out.printf("Elevator %d current floor: %d.%n", id, currentFloor);
        System.out.printf("Elevator %d direction: %s.%n", id, direction);
        System.out.printf("Elevator %d requests: %s.%n", id, requests);

        lastRequestTime = System.currentTimeMillis();
    }


    private int getNextFloor() {
        if (requests.isEmpty()) {
            return currentFloor;
        }

        // Create a list of requests to be removed
        List<Integer> toRemove = new ArrayList<>();

        // Sort requests in ascending order
        Collections.sort(requests);

        int nextFloor = -1;
        int index = -1;

        // Find the index of the next request in the current direction
        for (int i = 0; i < requests.size(); i++) {
            if (direction == Direction.UP && requests.get(i) >= currentFloor) {
                index = i;
                break;
            } else if (direction == Direction.DOWN && requests.get(i) <= currentFloor) {
                index = i;
            } else if (i == requests.size() - 1) {
                // No requests in the current direction, switch direction
                direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
                index = (direction == Direction.UP) ? 0 : requests.size() - 1;
            }
        }

        // Get the next floor
        if (index != -1) {
            nextFloor = requests.get(index);
            toRemove.add(nextFloor);
        }

        // Remove the requests to be removed
        for (int floor : toRemove) {
            requests.remove((Integer) floor);
        }

        return nextFloor;
    }





    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof OutsideRequest) {
            OutsideRequest outsideRequest = (OutsideRequest) arg;
            if (outsideRequest.getDirection() == Direction.UP && currentFloor < outsideRequest.getFloor()) {
                addRequest(outsideRequest.getFloor());
            } else if (outsideRequest.getDirection() == Direction.DOWN && currentFloor > outsideRequest.getFloor()) {
                addRequest(outsideRequest.getFloor());
            }
        } else if (arg instanceof InsideRequest) {
            InsideRequest insideRequest = (InsideRequest) arg;
            if (insideRequest.getDirection() == Direction.UP && currentFloor < insideRequest.getFloor()) {
                addRequest(insideRequest.getFloor());
            } else if (insideRequest.getDirection() == Direction.DOWN && currentFloor > insideRequest.getFloor()) {
                addRequest(insideRequest.getFloor());
            }
        }
    }
    }

