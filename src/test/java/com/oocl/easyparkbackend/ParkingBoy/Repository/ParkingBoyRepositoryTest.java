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
        ParkingBoy parkingBoy = new ParkingBoy( "username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", parkingLots);
        ParkingBoy returnParkingBoy = parkingBoyRepository.save(parkingBoy);

        ParkingBoy boy = parkingBoyRepository.findById(returnParkingBoy.getId()).orElse(null);

        Assertions.assertEquals(parkingBoy.getId(), boy.getId());
    }

    @Test
    void should_return_list_of_case_when_findByCaseName_given() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy( "username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", parkingLots);
        ParkingBoy returnParkingBoy = parkingBoyRepository.save(parkingBoy);

        ParkingBoy boy = parkingBoyRepository.findById(returnParkingBoy.getId()).orElse(null);

        Assertions.assertEquals(parkingBoy.getId(), boy.getId());
    }

    @Test
    void should_return_parkingBoy_when_getByEmailAndPassword_given_email_and_password() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy( "username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", parkingLots);
        ParkingBoy returnParkingBoy = parkingBoyRepository.save(parkingBoy);

        ParkingBoy boy = parkingBoyRepository.getByEmailAndPassword("953181215@qq.com", "199729").get();

        Assertions.assertEquals(returnParkingBoy.getId(), boy.getId());
    }

    @Test
    void should_return_parkingBoy_when_getByUsernameAndPassword_given_username_and_password() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy( "username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", parkingLots);
        ParkingBoy returnParkingBoy = parkingBoyRepository.save(parkingBoy);

        ParkingBoy boy = parkingBoyRepository.getByUsernameAndPassword("username", "199729").get();

        Assertions.assertEquals(returnParkingBoy.getId(), boy.getId());
    }

    @Test
    void should_return_parkingBoy_when_getByPhoneNumberAndPassword_given_phoneNumber_and_password() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingBoy parkingBoy = new ParkingBoy( "username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", parkingLots);
        ParkingBoy returnParkingBoy = parkingBoyRepository.save(parkingBoy);

        ParkingBoy boy = parkingBoyRepository.getByPhoneNumberAndPassword("13192269125", "199729").get();

        Assertions.assertEquals(returnParkingBoy.getId(), boy.getId());
    }

}
