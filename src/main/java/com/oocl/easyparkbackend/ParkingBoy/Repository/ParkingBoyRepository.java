package com.oocl.easyparkbackend.ParkingBoy.Repository;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Integer> {

    Optional<ParkingBoy> getByEmailAndPassword(String email, String password);

    Optional<ParkingBoy> getByPhoneNumberAndPassword(String phoneNumber, String password);

    Optional<ParkingBoy> getByUsernameAndPassword(String username, String password);

}
