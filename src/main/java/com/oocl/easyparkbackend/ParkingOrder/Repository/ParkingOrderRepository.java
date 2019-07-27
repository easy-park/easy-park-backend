package com.oocl.easyparkbackend.ParkingOrder.Repository;

import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder,String> {

    List<ParkingOrder> findAllByStatus(Integer status);
}