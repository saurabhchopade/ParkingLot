package com.bridgelabz.parkinglot.service;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.exception.ParkingLotException.ExceptionType;
import com.bridgelabz.parkinglot.observer.AirportSecurityImpl;
import com.bridgelabz.parkinglot.observer.ParkingLotObserver;
import com.bridgelabz.parkinglot.observer.ParkingOwnerImpl;

import java.util.ArrayList;
import java.util.List;
public class ParkingLot {
    private final int parkingLotCapacity;
    public static boolean status;
    public ParkingOwnerImpl parkingOwner = new ParkingOwnerImpl();
    public AirportSecurityImpl airportSecurity = new AirportSecurityImpl();
    public List parkingLotData = new ArrayList();
    ParkingLotObserver parkingLotObserver = new ParkingLotObserver();

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        parkingLotObserver.addIntoViewerList(parkingOwner);
        parkingLotObserver.addIntoViewerList(airportSecurity);
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (parkingLotData.contains(vehicle)) {
            throw new ParkingLotException(ExceptionType.VEHICLE_ALREADY_PARKED, "This vehicle already parked");
        }
        if (parkingLotData.size() == parkingLotCapacity) {
            parkingLotObserver.notificationUpdate(true);
            throw new ParkingLotException(ExceptionType.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        vehicleStatus(true);
        parkingLotData.add(vehicle);
    }

    public void UnPark(Object vehicle) throws ParkingLotException {
        if (!parkingLotData.contains(vehicle)) {
            throw new ParkingLotException(ExceptionType.NO_VEHICLE_PRESENT, "No such vehicle present");
        }
        parkingLotObserver.notificationUpdate(false);
        vehicleStatus(false);
    }

    public void vehicleStatus(boolean status) {
        ParkingLot.status = status;
    }
}
