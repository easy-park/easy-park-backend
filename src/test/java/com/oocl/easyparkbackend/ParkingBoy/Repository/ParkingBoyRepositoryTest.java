package com.oocl.easyparkbackend.ParkingBoy.Repository;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ParkingBoyRepositoryTest {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Test
    void should_return_list_of_case_when_findByCaseName_given_caseName() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy("1234567","username","199729","stefan","13192269125",1,"953181215@qq.com",parkingLots);
        parkingBoyRepository.save(parkingBoy);

        ParkingBoy boy = parkingBoyRepository.findById("1234567").orElse(null);

        Assertions.assertEquals(parkingBoy.getId(), boy.getId());
    }

}
