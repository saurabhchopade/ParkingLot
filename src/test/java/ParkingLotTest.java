import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.model.VehicleDetails;
import com.bridgelabz.parkinglot.observer.AirportSecurityImpl;
import com.bridgelabz.parkinglot.observer.ParkingOwnerImpl;
import com.bridgelabz.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
public class ParkingLotTest {
    Object firstVehicle;
    Object secondVehicle;
    ParkingLot parkingLot;

    @Before
    public void setUp() {
        new Object();
        parkingLot = new ParkingLot(2);
        firstVehicle = new Object();
        secondVehicle = new Object();
    }

    @Test
    public void givenAVehicle_IfParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.park(new VehicleDetails("MH-2222"));
        boolean parkingStatus = ParkingLot.status;
        Assert.assertTrue(parkingStatus);
    }

    @Test
    public void givenAVehicle_IfUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.park(new VehicleDetails("MH-2222"));
        parkingLot.park(new VehicleDetails("MH-1111"));
        parkingLot.UnPark(new VehicleDetails("MH-1111"));
        boolean unParkingStatus = ParkingLot.status;
        Assert.assertFalse(unParkingStatus);
    }

    @Test
    public void givenAVehicle_IfNotPresentAndWantToUnParke_ShouldHandleException() {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.UnPark(new VehicleDetails("MH-1111"));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }

    @Test
    public void givenVehicle_IfParkingLotIsFull_ShouldHandleException() {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.park(new VehicleDetails("MH-1111"));
            parkingLot.park(new VehicleDetails("MH-3333"));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenVehicle_IfParkingLotIsFullInformToOwner_ShouldHandleException() {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.park(new VehicleDetails("MH-1111"));
            parkingLot.park(new VehicleDetails("MH-3333"));
        } catch (ParkingLotException e) {
            Assert.assertTrue(ParkingOwnerImpl.status);
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenVehicle_IfAlreadyPresent_ShouldHandleException() throws ParkingLotException {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.park(new VehicleDetails("MH-2222"));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenVehicle_IfParkingLotIsFullInformToAirportSecurity_ShouldHandleException() {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.park(new VehicleDetails("MH-1111"));
            parkingLot.park(new VehicleDetails("MH-3333"));
        } catch (ParkingLotException e) {
            Assert.assertTrue(AirportSecurityImpl.status);
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenVehicle_IfParkingLotNowAvailable_ShouldReturnFalse() throws ParkingLotException {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.park(new VehicleDetails("MH-1111"));
            parkingLot.park(new VehicleDetails("MH-3333"));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
            parkingLot.UnPark(new VehicleDetails("MH-1111"));
            Assert.assertFalse(ParkingOwnerImpl.status);
        }
    }

    @Test
    public void givenVehicle_IfSpaceIsAvailable_ShouldReturnParkingLotNumberNo() throws ParkingLotException {
        parkingLot.park(new VehicleDetails("MH-2222"));
        int lotNum = parkingLot.allocateLotNo("MH-1111");
        parkingLot.park(new VehicleDetails("MH-1111"));
        Assert.assertEquals(5, lotNum);
    }

    @Test
    public void givenVehicle_AlreadyParkedAndWantsToAllocateLot_ShouldHandleException() {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.allocateLotNo("MH-2222");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenVehicle_CheckVehiclePresentOrNotIfParked_ShouldReturnParkingLotNumber() {
        try {
            parkingLot.park(new VehicleDetails("A"));
            boolean lotNo = parkingLot.isMyVehiclePresent("A");
            Assert.assertTrue(lotNo);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }

    @Test
    public void givenVehicle_CheckVehiclePresentOrNotIfNotParked_ShouldHandleException() {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.isMyVehiclePresent("MH-2222");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }

    @Test
    public void givenVehicleDetails_IfVehicleIsParkedAlready_ShouldReturnParkingTime() throws ParkingLotException {
        parkingLot.park(new VehicleDetails("MH-2222"));
        parkingLot.park(new VehicleDetails("MH-1111"));
        LocalTime time = parkingLot.giveVehicleParkingTiming("MH-1111");
    }

    @Test
    public void givenVehicleDetails_IfVehicleIsNotParked_ShouldReturnHandleException() throws ParkingLotException {
        try {
            parkingLot.park(new VehicleDetails("MH-2222"));
            parkingLot.park(new VehicleDetails("MH-1111"));
            LocalTime time = parkingLot.giveVehicleParkingTiming("MH-6666");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }
}


