import com.bridgelabz.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class ParkingLotTest {
    Object vehicle;
    ParkingLot parkingLotSystem;

    @Before
    public void setUp() {
        new Object();
        new ParkingLot();
    }

    @Test
    public void givenAVehicle_IfParked_ShouldReturnTrue() {
        boolean isParked = parkingLotSystem.park(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenAVehicle_IfUnParked_ShouldReturnTrue() {
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.UnPark(vehicle);
        Assert.assertFalse(isUnParked);
    }
}


