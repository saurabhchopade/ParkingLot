package com.bridgelabz.parkinglot.model;
import com.bridgelabz.parkinglot.enums.DriverType;

import java.time.LocalTime;
public class VehicleDetails {
    public LocalTime vehicleParkingTime;
    public String vehicleNumber;
    public DriverType driverType;

    public VehicleDetails(String vehicleNumber, DriverType driverType) {
        this.vehicleParkingTime = LocalTime.now();
        this.vehicleNumber = vehicleNumber;
        this.driverType = driverType;
    }
}
