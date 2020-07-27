package com.bridgelabz.parkinglot.exception;
public class ParkingLotException extends Exception {
    public enum ExceptionType {
        PARKING_LOT_IS_FULL, VEHICLE_ALREADY_PARKED, VEHICLE_NOT_PARKED, NO_SUCHTYPE_VEHICLEFOUND;
    }

    public ExceptionType type;

    /**
     * It give Type Based On Exception
     *
     * @param type
     * @param message
     */
    public ParkingLotException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
