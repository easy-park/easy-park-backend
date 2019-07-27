package com.oocl.easyparkbackend.ParkingOrder.Service;

import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingOrderService {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    public List<ParkingOrder> findParkingOrderByStatus(int status) {
        return parkingOrderRepository.findAllByStatus(status);
    }
}
