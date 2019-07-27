package com.oocl.easyparkbackend.ParkingBoy.Repository;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, String > {

}
