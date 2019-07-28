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
        List<ParkingLot> returnParkingLotList = parkingBoyRepository.findById(id).get().getParkingLotList();
        if (status == 1 && parkingLotListIsFull(returnParkingLotList)) {
            return null;
        }
        return parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy.get(), status);
    }

    private boolean parkingLotListIsFull(List<ParkingLot> parkingLotList) {
        return parkingLotList.stream().map(ParkingLot::getAvailable).reduce(0,(a,b) -> a + b)==0;
    }

    public ParkingOrder updateParkingOrderStatus(String orderId,int status) {
        ParkingOrder parkingOrder = parkingOrderRepository.findById(orderId).get();
        ParkingBoy parkingBoy = parkingOrder.getParkingBoy();
        switch (status){
            case 3:
                parkingBoy.setStatus(0);
                break;
            case 5:
                parkingBoy.setStatus(1);
                break;
            case 6:
                parkingBoy.setStatus(0);
                break;
        }
        parkingBoyRepository.save(parkingBoy);
        parkingOrder.setStatus(status);
        parkingOrder.setParkingBoy(parkingBoy);
        return parkingOrderRepository.save(parkingOrder);
    }
}
