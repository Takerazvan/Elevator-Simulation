package com.codecool.elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ControlPanel extends Observable {
    private final int numFloors;
    private final List<Request> requests;

    public ControlPanel(int numFloors) {
        this.numFloors = numFloors;
        this.requests = new ArrayList<>();
    }

    public void addRequest(Request... requests) {
        for (Request request : requests) {
            if (request.getFloor() < numFloors) {
                this.requests.add(request);
                setChanged();
                notifyObservers(request);
            }
        }
    }


    public boolean hasRequests() {
        return !requests.isEmpty();
    }
}
