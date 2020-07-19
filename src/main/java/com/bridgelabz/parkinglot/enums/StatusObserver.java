package com.bridgelabz.parkinglot.enums;
public enum StatusObserver {
    OWNER(false), AIRPORT_SECURITY(false);
    public boolean isParkingFull;

    StatusObserver(boolean isParkingFull) {
        this.isParkingFull = isParkingFull;
    }
}
