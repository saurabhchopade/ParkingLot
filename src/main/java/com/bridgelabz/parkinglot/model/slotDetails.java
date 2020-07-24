package com.bridgelabz.parkinglot.model;
import com.bridgelabz.parkinglot.enums.DriverType;

import java.time.LocalDateTime;
public class slotDetails {
    public LocalDateTime vehicleParkingTime;
    public String vehicleNumber;
    public DriverType driverType;

    public slotDetails(String vehicleNumber, DriverType driverType) {
        this.vehicleParkingTime = LocalDateTime.now();
        this.vehicleNumber = vehicleNumber;
        this.driverType = driverType;
    }
}
