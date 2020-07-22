package com.bridgelabz.parkinglot.observer;
public class ParkingOwnerImpl implements ParkingObserver {
    public static boolean status;
    @Override
    public void updateParkingLotStatus(boolean status) {
        ParkingOwnerImpl.status =status;
    }
}
