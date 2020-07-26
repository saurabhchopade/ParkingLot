package com.bridgelabz.parkinglot.model;
import com.bridgelabz.parkinglot.enums.DriverType;
import com.bridgelabz.parkinglot.enums.VehicleColor;
import com.bridgelabz.parkinglot.enums.VehicleMake;
import com.bridgelabz.parkinglot.enums.VehicleSize;

import java.time.LocalDateTime;
public class ParkingSlotDetails {
    public LocalDateTime vehicleParkingTime;
    public String vehicleNumber;
    public VehicleSize vehicleSize;
    public DriverType driverType;
    public VehicleColor vehicleColor;
    public VehicleMake vehicleMake;

    public ParkingSlotDetails(String vehicleNumber, DriverType driverType, VehicleSize vehicleSize,
                              VehicleColor vehicleColor, VehicleMake vehicleMake) {
        this.vehicleParkingTime = LocalDateTime.now();
        this.vehicleNumber = vehicleNumber;
        this.vehicleSize = vehicleSize;
        this.driverType = driverType;
        this.vehicleColor = vehicleColor;
        this.vehicleMake = vehicleMake;
    }
}
