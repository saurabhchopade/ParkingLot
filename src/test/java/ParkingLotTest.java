import com.bridgelabz.parkinglot.service.ParkingLot;
import org.junit.Assert;
import org.junit.Test;
public class ParkingLotTest {
    @Test
    public void givenAVehicle_IfParked_ShouldReturnTrue() {
        ParkingLot parkingLot=new ParkingLot();
        boolean isParked = parkingLot.park(new Object());
        Assert.assertTrue(isParked);
    }
}
