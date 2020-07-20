package com.bridgelabz.parkinglot.exception;
public class ParkingLotException extends Exception {
    public enum ExceptionType {
        PARKING_LOT_IS_FULL, NO_VEHICLE_PRESENT,VEHICLE_ALREADY_PARKED;
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
