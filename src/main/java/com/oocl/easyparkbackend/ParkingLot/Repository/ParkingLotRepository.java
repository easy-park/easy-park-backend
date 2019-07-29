package com.oocl.easyparkbackend.ParkingLot.Repository;

import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,String> {
    List<ParkingLot> findByNameLike(String name);
}
