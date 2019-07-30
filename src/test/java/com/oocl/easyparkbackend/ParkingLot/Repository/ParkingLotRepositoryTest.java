package com.oocl.easyparkbackend.ParkingLot.Repository;

import com.oocl.easyparkbackend.ParkingLot.Controller.ParkingLotController;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ParkingLotRepositoryTest {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_return_parkingLots_when_invoke_findByNameLike_given_name() {
        ParkingLot parkingLot = new ParkingLot("123","parkinglot1",20,10);
        parkingLotRepository.save(parkingLot);

        List<ParkingLot> returnParkingLots = parkingLotRepository.findByNameLike("%lot%");

        assertThat(returnParkingLots.get(0).getName().equals(parkingLot.getName()));
    }

    @Test
    public void should_update_parkingLot_when_invoke_updateParkingLot_given_parkingLot() {
        ParkingLot parkingLot = new ParkingLot("123", "parkingLot2", 10, 10);
        parkingLotRepository.save(parkingLot);

        ParkingLot updateParkingLot = new ParkingLot("123", "parkingLot3", 11, 10);
        parkingLotRepository.save(updateParkingLot);
        ParkingLot fetchedParkingLot = parkingLotRepository.findById("123").get();

        assertThat(fetchedParkingLot.getAvailable().equals(updateParkingLot.getAvailable()));
        assertThat(fetchedParkingLot.getName()).isNotEqualTo(parkingLot.getName());
        assertThat(fetchedParkingLot.getCapacity()).isNotEqualTo(parkingLot.getCapacity());

    }
}
