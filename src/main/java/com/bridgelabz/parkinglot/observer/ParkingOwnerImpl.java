package com.bridgelabz.parkinglot.observer;
public class ParkingOwnerImpl implements ParkingObserver {
    public static boolean status;
    public static int lotNum;

    @Override
    public void updateParkingLotStatus(boolean status) {
        ParkingOwnerImpl.status =status;
    }
    public static void lotNoForCar(int lotNum)
    {
        ParkingOwnerImpl.lotNum=lotNum;
    }
}
