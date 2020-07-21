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
    private final int parkingLotCapacity;
    public static boolean status;
    public static Integer lotNo = 1;
    public ParkingOwnerImpl parkingOwner = new ParkingOwnerImpl();
    public AirportSecurityImpl airportSecurity = new AirportSecurityImpl();
    ParkingLotObserver parkingLotObserver = new ParkingLotObserver();

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        parkingLotObserver.addIntoViewerList(parkingOwner);
        parkingLotObserver.addIntoViewerList(airportSecurity);
    }

    public void park(VehicleDetails vehicle) throws ParkingLotException {
        if (this.checkPresent(vehicle)) {
            throw new ParkingLotException(ExceptionType.VEHICLE_ALREADY_PARKED, "This vehicle already parked");
        }
        if (parkingLotData.size() == parkingLotCapacity) {
            parkingLotObserver.notificationUpdate(true);
            throw new ParkingLotException(ExceptionType.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        parkingLotData.put(lotNo, vehicle);
        lotNo++;
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

    public int allocateLotNo(String vehicleNumber) throws ParkingLotException {
        for (VehicleDetails t : parkingLotData.values()) {
            if (t.vehicleNumber == vehicleNumber) {
                throw new ParkingLotException(ExceptionType.VEHICLE_ALREADY_PARKED, "Vehicle already present");
            }
        }
        return lotNo + 1;
    }

    public boolean isMyVehiclePresent(String vehicleNumber) throws ParkingLotException {
        for (VehicleDetails t : parkingLotData.values()) {
            if (t.vehicleNumber == vehicleNumber) {
                return true;
            }
        }
        throw new ParkingLotException(ExceptionType.VEHICLE_NOT_PARKED, "Vehicle Not present");
    }

    public boolean checkPresent(VehicleDetails vehicle) {
        for (VehicleDetails t : parkingLotData.values()) {
            if (t.vehicleNumber == vehicle.vehicleNumber) {
                return true;
            }
        }
        return false;
    }

    public LocalTime giveVehicleParkingTiming(String givenCarName) throws ParkingLotException {
        for (VehicleDetails t : parkingLotData.values()) {
            if (t.vehicleNumber == givenCarName) {
                return t.vehicleParkingTime;
            }
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, "Vehicle Not present");
    }
}


