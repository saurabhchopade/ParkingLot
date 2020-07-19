package com.bridgelabz.parkinglot.service;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.exception.ParkingLotException.ExceptionType;

import java.util.ArrayList;
import java.util.List;
public class ParkingLot {
    private int parkingLotCapacity;
    public static boolean status;
    List parkingLotData = new ArrayList();

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (parkingLotData.size() == parkingLotCapacity) {
            throw new ParkingLotException(ExceptionType.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        vehicleStatus(true);
        parkingLotData.add(vehicle);
    }

    public void UnPark(Object vehicle) throws ParkingLotException {
        if (parkingLotData.contains(vehicle)) {
            vehicleStatus(false);
        }
        throw new ParkingLotException(ExceptionType.NO_VEHICLE_PRESENT, "No such vehicle present");
    }

    public boolean vehicleStatus(boolean status) {
        return this.status = status;
    }
}
