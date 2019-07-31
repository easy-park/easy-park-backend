package com.oocl.easyparkbackend.ParkingLot.Repository;

import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        ParkingLot parkingLot = new ParkingLot("parkingLot2", 10, 10);
        ParkingLot parkingLotSave = parkingLotRepository.save(parkingLot);
        parkingLotSave.setName("parkingLot3");
        parkingLotSave.setAvailable(10);
        parkingLotSave.setCapacity(11);
        parkingLotRepository.save(parkingLotSave);
        ParkingLot fetchedParkingLot = parkingLotRepository.findById(parkingLotSave.getId()).get();

        assertThat(fetchedParkingLot.getAvailable().equals(parkingLot.getAvailable()));
        assertThat(fetchedParkingLot.getName()).isNotEqualTo("parkingLot2");
        assertThat(fetchedParkingLot.getCapacity()).isNotEqualTo(10);

    }

    @Test
    public void should_return_corresponding_parkingLots_given_capacity_range() {
        ParkingLot parkingLot1 = new ParkingLot("123", "parkingLot2", 10, 10);
        ParkingLot parkingLot2 = new ParkingLot("124", "parkingLot2", 27, 10);
        ParkingLot parkingLot3 = new ParkingLot("125", "parkingLot2", 80, 10);
        parkingLotRepository.save(parkingLot1);
        parkingLotRepository.save(parkingLot2);
        parkingLotRepository.save(parkingLot3);
        List<ParkingLot> returnParkingLots = new ArrayList<>();
        returnParkingLots.add(parkingLot1);
        returnParkingLots.add(parkingLot2);

        int start = 10;
        int end = 30;
        List<ParkingLot> parkingLots = parkingLotRepository.findByCapacityBetween(start, end);

        assertEquals(parkingLots.size(), 2);
        assertThat(parkingLots.get(1).getName()).isEqualTo(returnParkingLots.get(1).getName());
    }

}
