import com.bridgelabz.parkinglot.enums.StatusObserver;
import com.bridgelabz.parkinglot.exception.ParkingLotException;
import com.bridgelabz.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class ParkingLotTest {
    Object vehicle;
    Object anotherVehicle;
    ParkingLot parkingLot;

    @Before
    public void setUp() {
        new Object();
        parkingLot = new ParkingLot(1);
        anotherVehicle = new Object();
    }

    @Test
    public void givenAVehicle_IfParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.park(vehicle);
        boolean parkingStatus = ParkingLot.status;
        Assert.assertTrue(parkingStatus);
    }

    @Test
    public void givenAVehicle_IfUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.park(vehicle);
        parkingLot.UnPark(vehicle);
        boolean unParkingStatus = ParkingLot.status;
        Assert.assertFalse(unParkingStatus);
    }

    @Test
    public void givenAVehicle_IfNotPresentAndWantToUnParke_ShouldHandleException() {
        try {
            parkingLot.park(vehicle);
            Object unknownVehicle = new Object();
            parkingLot.UnPark(unknownVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_PRESENT, e.type);
        }
    }

    @Test
    public void givenVehicle_IfParkingLotIsFull_ShouldHandleException() {
        try {
            parkingLot.park(vehicle);
            Object anotherVehicle = new Object();
            parkingLot.park(anotherVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenVehicle_IfParkingLotIsFullInformToOwner_ShouldHandleException() {
        try {
            parkingLot.park(vehicle);
            Object anotherVehicle = new Object();
            parkingLot.park(anotherVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(true, StatusObserver.OWNER.isParkingFull);
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenVehicle_IfParkingLotIsFullInformToAirportSecurity_ShouldHandleException() {
        try {
            parkingLot.park(vehicle);
            Object anotherVehicle = new Object();
            parkingLot.park(anotherVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(true, StatusObserver.AIRPORT_SECURITY.isParkingFull);
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenVehicle_IfParkingLotNowAvailable_ShouldReturnFalse() throws ParkingLotException {
        try {
            parkingLot.park(vehicle);
            parkingLot.park(anotherVehicle);
        } catch (ParkingLotException e) {
            parkingLot.UnPark(vehicle);
            Assert.assertEquals(false, StatusObserver.OWNER.isParkingFull);
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }
}


