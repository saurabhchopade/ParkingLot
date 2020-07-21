package com.bridgelabz.parkinglot.model;
import java.time.LocalTime;
public class VehicleDetails {
    public LocalTime vehicleParkingTime;
    public String vehicleNumber;

    public VehicleDetails(String vehicleNumber) {
        this.vehicleParkingTime = LocalTime.now();
        this.vehicleNumber = vehicleNumber;
    }
}
