package com.oocl.easyparkbackend.ParkingOrder.Service;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingOrderService {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    public List<ParkingOrder> findParkingOrderByStatus(String id, int status) {
        Optional<ParkingBoy> parkingBoy = parkingBoyRepository.findById(id);
        List<ParkingLot> returnParkingLotList = parkingBoyRepository.findById("123").get().getParkingLotList();
        if (status == 1 && parkingLotListIsFull(returnParkingLotList)) {
            return null;
        }
        return parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy.get(), status);
    }

    private boolean parkingLotListIsFull(List<ParkingLot> parkingLotList) {
        return parkingLotList.stream().map(ParkingLot::getAvailable).reduce(0,(a,b) -> a + b)==0;
    }
}
