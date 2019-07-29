package com.oocl.easyparkbackend.ParkingBoy.Repository;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Integer> {

    Optional<ParkingBoy> getByEmailAndPassword(String email, String password);

    Optional<ParkingBoy> getByPhoneNumberAndPassword(String phoneNumber, String password);

    Optional<ParkingBoy> getByUsernameAndPassword(String username, String password);

    List<ParkingBoy> findByNameLike(String name);

    List<ParkingBoy> findByPhoneNumberLike(String phoneNumber);

    List<ParkingBoy> findByEmailLike(String email);
}
