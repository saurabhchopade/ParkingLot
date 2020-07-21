package com.bridgelabz.parkinglot.service;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.exception.ParkingLotException.ExceptionType;
import com.bridgelabz.parkinglot.model.VehicleDetails;
import com.bridgelabz.parkinglot.observer.AirportSecurityImpl;
import com.bridgelabz.parkinglot.observer.ParkingLotObserver;
import com.bridgelabz.parkinglot.observer.ParkingOwnerImpl;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
public class ParkingLot {
    Map<Integer, VehicleDetails> parkingLotData = new HashMap<>();
    private final int TOTAL_PARKING_LOT_CAPACITY;
    private final int TOTAL_LOTS;
    private final int SINGLE_LOT_CAPACITY;
    public static boolean status;
    public static Integer lotNo = 1;
    public ParkingOwnerImpl parkingOwner = new ParkingOwnerImpl();
    public AirportSecurityImpl airportSecurity = new AirportSecurityImpl();
    ParkingLotObserver parkingLotObserver = new ParkingLotObserver();

    public ParkingLot(int parkingLotCapacity, int noOfLots) {
        this.TOTAL_LOTS = noOfLots;
        this.TOTAL_PARKING_LOT_CAPACITY = parkingLotCapacity * noOfLots;
        this.SINGLE_LOT_CAPACITY = parkingLotCapacity;
        parkingLotObserver.addIntoViewerList(parkingOwner);
        parkingLotObserver.addIntoViewerList(airportSecurity);
    }

    public void park(VehicleDetails vehicle) throws ParkingLotException {
        if (this.checkPresent(vehicle)) {
            throw new ParkingLotException(ExceptionType.VEHICLE_ALREADY_PARKED, "This vehicle already parked");
        }
        if (parkingLotData.size() == TOTAL_PARKING_LOT_CAPACITY) {
            parkingLotObserver.notificationUpdate(true);
            throw new ParkingLotException(ExceptionType.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        this.allocateLotNo(vehicle);
        vehicleStatus(true);
    }

    public void UnPark(VehicleDetails vehicle) throws ParkingLotException {
        if (!isMyVehiclePresent(vehicle.vehicleNumber)) {
            throw new ParkingLotException(ExceptionType.NO_VEHICLE_PRESENT, "No such vehicle present");
        }
        for (Map.Entry<Integer, VehicleDetails> entry : parkingLotData.entrySet()) {
            if (vehicle.equals(entry.getValue())) {
                Integer key = entry.getKey();
                parkingLotData.remove(key);
                break;
            }
        }
        parkingLotObserver.notificationUpdate(false);
    }

    public void vehicleStatus(boolean status) {
        ParkingLot.status = status;
    }

    public void allocateLotNo(VehicleDetails vehicle) throws ParkingLotException {
        if (this.checkPresent(vehicle)) {
            throw new ParkingLotException(ExceptionType.VEHICLE_ALREADY_PARKED, "This vehicle already parked");
        }
        int start = SINGLE_LOT_CAPACITY * lotNo - TOTAL_PARKING_LOT_CAPACITY - 1;
        int previousLengthOfMap = parkingLotData.size();
        for (int key = start; key < SINGLE_LOT_CAPACITY * lotNo; key++) {
            parkingLotData.putIfAbsent(key, vehicle);
            if (parkingLotData.size() > previousLengthOfMap) {
                lotNo++;
                break;
            }
        }
        if (lotNo == TOTAL_LOTS + 1) {
            lotNo = 1;
        }
    }

    public boolean isMyVehiclePresent(String vehicleNumber) throws ParkingLotException {
        for (VehicleDetails t : parkingLotData.values()) {
            if (t.vehicleNumber.equals(vehicleNumber)) {
                return true;
            }
        }
        throw new ParkingLotException(ExceptionType.VEHICLE_NOT_PARKED, "Vehicle Not present");
    }

    public boolean checkPresent(VehicleDetails vehicle) {
        for (VehicleDetails t : parkingLotData.values()) {
            if (t.vehicleNumber.equals(vehicle.vehicleNumber)) {
                return true;
            }
        }
        return false;
    }

    public LocalTime giveVehicleParkingTiming(String givenCarName) throws ParkingLotException {
        for (VehicleDetails t : parkingLotData.values()) {
            if (t.vehicleNumber.equals(givenCarName)) {
                return t.vehicleParkingTime;
            }
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, "Vehicle Not present");
    }
}


