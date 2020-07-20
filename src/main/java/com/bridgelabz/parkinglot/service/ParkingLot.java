package com.bridgelabz.parkinglot.service;
import com.bridgelabz.parkinglot.enums.StatusObserver;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.exception.ParkingLotException.ExceptionType;

import java.util.ArrayList;
import java.util.List;
public class ParkingLot {
    private final int parkingLotCapacity;
    public static boolean status;
    List parkingLotData = new ArrayList();

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (parkingLotData.contains(vehicle)) {
            throw new ParkingLotException(ExceptionType.VEHICLE_ALREADY_PARKED, "This vehicle already parked");
        }
        if (parkingLotData.size() == parkingLotCapacity) {
            StatusObserver.PARKING_LOT_OWNER.isParkingFull = true;
            StatusObserver.AIRPORT_SECURITY.isParkingFull = true;
            throw new ParkingLotException(ExceptionType.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        vehicleStatus(true);
        parkingLotData.add(vehicle);
    }

    public void UnPark(Object vehicle) throws ParkingLotException {
        if (!parkingLotData.contains(vehicle)) {
            throw new ParkingLotException(ExceptionType.NO_VEHICLE_PRESENT, "No such vehicle present");
        }
        StatusObserver.PARKING_LOT_OWNER.isParkingFull = false;
        vehicleStatus(false);
    }

    public boolean vehicleStatus(boolean status) {
        return this.status = status;
    }
}
