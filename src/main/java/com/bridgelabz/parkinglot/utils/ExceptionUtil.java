package com.bridgelabz.parkinglot.utils;
import com.bridgelabz.parkinglot.exception.ParkingLotException;

import java.util.List;
import java.util.Map;
public class ExceptionUtil {
    public static void listException(List list) throws ParkingLotException {
        if (list.size() == 0) {
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND,
                    "No such type " + "of vehicle present");
        }
    }

    public static void mapException(Map map) throws ParkingLotException {
        if (map.size() == 0) {
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND,
                    "No such type " + "of vehicle present");
        }
    }
}
