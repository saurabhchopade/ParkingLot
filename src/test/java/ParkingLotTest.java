import com.bridgelabz.parkinglot.enums.DriverType;import com.bridgelabz.parkinglot.enums.VehicleColor;import com.bridgelabz.parkinglot.enums.VehicleMake;import com.bridgelabz.parkinglot.enums.VehicleSize;import com.bridgelabz.parkinglot.exception.ParkingLotException;import com.bridgelabz.parkinglot.model.ParkingSlotDetails;import com.bridgelabz.parkinglot.observer.AirportSecurityImpl;import com.bridgelabz.parkinglot.observer.ParkingOwnerImpl;import com.bridgelabz.parkinglot.service.ParkingLot;import org.junit.Assert;import org.junit.Before;import org.junit.Test;import java.time.LocalDateTime;import java.time.format.DateTimeFormatter;import java.util.List;import java.util.Map;public class ParkingLotTest {    ParkingLot parkingLot;    @Before    public void setUp() {        parkingLot = new ParkingLot(10, 4);    }    @Test    public void givenAVehicle_IfParked_ShouldReturnTrue() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLACK, VehicleMake.TATAMOTORS));            boolean parkingStatus = ParkingLot.LatestVehicleStatus;            Assert.assertTrue(parkingStatus);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenAVehicle_IfUnParked_ShouldReturnFalse() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.TATAMOTORS));            parkingLot.park(new ParkingSlotDetails("MH-1111", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.MARUTISUZUKI));            parkingLot.unPark("MH-2222");            boolean unParkingStatus = ParkingLot.LatestVehicleStatus;            Assert.assertFalse(unParkingStatus);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);        }    }    @Test    public void givenAVehicle_IfNotPresentAndWantToUnParke_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.RED, VehicleMake.MARUTISUZUKI));            parkingLot.unPark("MH-1111");        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);        }    }    @Test    public void givenVehicle_IfParkingLotIsFull_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-1111", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TATAMOTORS));            parkingLot.park(new ParkingSlotDetails("MH-3333", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);        }    }    @Test    public void givenVehicle_IfParkingLotIsFullInformToOwner_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-1111", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TATAMOTORS));            parkingLot.park(new ParkingSlotDetails("MH-3333", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));        } catch (ParkingLotException e) {            Assert.assertTrue(ParkingOwnerImpl.status);            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);        }    }    @Test    public void givenVehicle_IfAlreadyPresent_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenVehicle_IfParkingLotIsFullInformToAirportSecurity_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-1111", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-3333", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));        } catch (ParkingLotException e) {            Assert.assertTrue(AirportSecurityImpl.status);            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);        }    }    @Test    public void givenVehicle_IfParkingLotNowAvailable_ShouldReturnFalse() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-1111", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-3333", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);            try {                parkingLot.unPark("MH-1111");            } catch (ParkingLotException exception) {                exception.printStackTrace();            }            Assert.assertFalse(ParkingOwnerImpl.status);        }    }    @Test    public void givenVehicle_IfSpaceIsAvailable_ShouldUpdateParkingLotNumberNoToOwner() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            int slotNo = parkingLot.allocateAvailableLot(new ParkingSlotDetails("MH-3333",                    DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL, VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            Assert.assertEquals(11, slotNo);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenVehicle_AlreadyParkedAndWantsToAllocateLot_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.allocateAvailableLot(new ParkingSlotDetails("MH-3333", DriverType.NON_HANDICAP_DRIVER,                    VehicleSize.SMALL, VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenVehicle_CheckVehiclePresentOrNotIfParked_ShouldReturnParkingLotNumber() {        try {            parkingLot.park(new ParkingSlotDetails("A", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            boolean lotNo = parkingLot.isMyVehiclePresent("A");            Assert.assertTrue(lotNo);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);        }    }    @Test    public void givenVehicle_CheckVehiclePresentOrNotIfNotParked_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            boolean parkingStatusOfVehicle = parkingLot.isMyVehiclePresent("MH-2222");            Assert.assertTrue(parkingStatusOfVehicle);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);        }    }    @Test    public void givenVehicleDetails_IfVehicleIsParkedAlready_ShouldReturnParkingTime() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-1111", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            LocalDateTime parkedTime = parkingLot.vehicleArrivedTime("MH-1111");            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");            String parkedDateTime = parkedTime.format(format1);            LocalDateTime currentTime = LocalDateTime.now();            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");            String currentDateTime = currentTime.format(format2);            Assert.assertEquals(parkedDateTime, currentDateTime);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);        }    }    @Test    public void givenVehicleDetails_IfVehicleIsNotParkedWhileCheckingForVehicleTiming_ShouldReturnHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-2222", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-6666", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.vehicleArrivedTime("MH-1111");        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);        }    }    @Test    public void givenVehicleDetails_IfVehicleParked_ShouldParkEvenly() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TATAMOTORS));            parkingLot.park(new ParkingSlotDetails("MH-55", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-66", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TATAMOTORS));            parkingLot.park(new ParkingSlotDetails("MH-67", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            int lotNo = ParkingOwnerImpl.lotNum;            Assert.assertEquals(3, lotNo);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenVehicleDetails_IfVehicleAlreadyParked_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TATAMOTORS));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.allocateAvailableLot(new ParkingSlotDetails("MH-33", DriverType.NON_HANDICAP_DRIVER,                    VehicleSize.SMALL, VehicleColor.WHITE, VehicleMake.TOYOTA));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenVehicle_IfDriverTypeIsHandicap_ShouldParkToNearestLot() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TATAMOTORS));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-55", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            int lotNo1 = ParkingOwnerImpl.lotNum;            Assert.assertEquals(4, lotNo1);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenVehicle_IfVehicleDriverTypeIsHandicap_ShouldParkToNearestLot() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-55", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            int allottedLotNo = ParkingOwnerImpl.lotNum;            Assert.assertEquals(2, allottedLotNo);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenVehicle_IfVehicleSizeIsLarge_ShouldAllocateHighestFreeSpace() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-55", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-66", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-77", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-88", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-99", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-100", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.MARUTISUZUKI));            parkingLot.unPark("MH-55");            int slotNoForLargeVehicle = parkingLot.allocateAvailableLot(new ParkingSlotDetails("MH-111",                    DriverType.HANDICAP_DRIVER, VehicleSize.LARGE, VehicleColor.WHITE, VehicleMake.TOYOTA));            Assert.assertEquals(11, slotNoForLargeVehicle);            int slotNoForSmallVehicle = parkingLot.allocateAvailableLot(new ParkingSlotDetails("MH-222",                    DriverType.HANDICAP_DRIVER, VehicleSize.SMALL, VehicleColor.WHITE, VehicleMake.HYUNDAI));            Assert.assertEquals(5, slotNoForSmallVehicle);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenRequestToGetWhiteVehicleLocations_IfVehicleFound_ShouldReturnListWhichContainsSlotNumbers() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLACK, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.MARUTISUZUKI));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.TOYOTA));            List<Integer> listOfVehicles = parkingLot.getVehicleLocationsByColor(VehicleColor.WHITE);            Assert.assertTrue(listOfVehicles.contains(1));            Assert.assertTrue(listOfVehicles.contains(4));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND, e.type);        }    }    @Test    public void givenRequestToGetWhiteVehicleLocations_IfSuchTypeOfVehicleNotFound_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLACK, VehicleMake.TOYOTA));            parkingLot.getVehicleLocationsByColor(VehicleColor.WHITE);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND, e.type);        }    }    @Test    public void givenRequestBasedOnColorAndMake_IfVehicleFound_ShouldReturnMapOfDetails() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.RED, VehicleMake.HYUNDAI));            Map<Integer, ParkingSlotDetails> listOfVehicle =                    parkingLot.getVehicleDetailsByColorAndMake(VehicleColor.BLUE, VehicleMake.TOYOTA);            Assert.assertTrue(listOfVehicle.containsKey(2));            Assert.assertTrue(listOfVehicle.containsKey(3));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND, e.type);        }    }    @Test    public void givenRequestBasedOnColorAndMake_IfVehicleNotFound_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLACK, VehicleMake.BMW));            parkingLot.getVehicleDetailsByColorAndMake(VehicleColor.BLUE, VehicleMake.TOYOTA);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND, e.type);        }    }    @Test    public void givenRequestToGetVehicleInfoByMake_IfVehicleFound_ShouldReturnDetailsOfThatVehicle() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.BMW));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.RED, VehicleMake.HYUNDAI));            List<Integer> listOfVehicles = parkingLot.getVehicleDetailsByMake(VehicleMake.BMW);            Assert.assertTrue(listOfVehicles.contains(3));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND, e.type);        }    }    @Test    public void givenRequestToGetVehicleInfoByMake_IfVehicleNotFound_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.getVehicleDetailsByMake(VehicleMake.BMW);        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND, e.type);        }    }    @Test    public void givenRequestToGetVehicleParkedInLastGivenMinutes_IfVehicleFound_ShouldReturnListOfVehicles() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.BMW));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.RED, VehicleMake.HYUNDAI));            List<Integer> listOfVehicles = parkingLot.giveVehiclesParkedInLast30minutes(30);            Assert.assertTrue(listOfVehicles.contains(1));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenRequestToGetAllSmallHandicapVehicleFromRowSelected_IfVehicleFound_ShouldReturnAllDetails() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.BMW));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.RED, VehicleMake.BMW));            Map<Integer, ParkingSlotDetails> listOfVehicles = parkingLot.giveVehicleDetailsBySizeAndDriverType(1);            Assert.assertEquals(1, listOfVehicles.size());        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }    @Test    public void givenRequestToGetAllSmallHandicapVehicleFromSelectedRow_IfVehicleNotFound_ShouldHandleException() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.BMW));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.RED, VehicleMake.BMW));            Map<Integer, ParkingSlotDetails> listOfVehicles = parkingLot.giveVehicleDetailsBySizeAndDriverType(1);            Assert.assertEquals(1, listOfVehicles.size());        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCHTYPE_VEHICLEFOUND, e.type);        }    }    @Test    public void givenRequestToGetAllVehicleDetails_IfVehiclesFound_ShouldReturnAllVehicleDetails() {        try {            parkingLot.park(new ParkingSlotDetails("MH-11", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.WHITE, VehicleMake.HYUNDAI));            parkingLot.park(new ParkingSlotDetails("MH-22", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.TOYOTA));            parkingLot.park(new ParkingSlotDetails("MH-33", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.BLUE, VehicleMake.BMW));            parkingLot.park(new ParkingSlotDetails("MH-44", DriverType.NON_HANDICAP_DRIVER, VehicleSize.SMALL,                    VehicleColor.RED, VehicleMake.BMW));            Map<Integer, ParkingSlotDetails> listOfDetails = parkingLot.giveAllVehicleDetails();            Assert.assertEquals(4, listOfDetails.size());            Assert.assertTrue(listOfDetails.containsKey(1));        } catch (ParkingLotException e) {            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);        }    }}