package com.bridgelabz.parkinglot.observer;
import java.util.ArrayList;
import java.util.List;
public class ParkingLotObserver {
    private List<ParkingObserver> statusObservers = new ArrayList<>();

    public void registerForStatus(ParkingObserver viewer) {
        statusObservers.add(viewer);
    }

    public void notificationUpdate(boolean notify) {
        statusObservers.forEach(viewer -> viewer.updateParkingLotStatus(notify));
    }
}
