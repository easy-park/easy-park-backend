package com.oocl.easyparkbackend.ParkingOrder.Service;

import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.LoginTokenExpiredException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotIdErrorException;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Exception.ParkingOrderIdErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Repository.ParkingOrderRepository;
import com.oocl.easyparkbackend.common.vo.ParkingBoyStatus;
import com.oocl.easyparkbackend.common.vo.ParkingOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingOrderService {

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private UserOperator userOperator;

    public List<ParkingOrder> findParkingOrderByStatus( int status) {
        User user = userOperator.getUser();
        List<ParkingOrder> parkingOrderList = new ArrayList<>();
        Optional<ParkingBoy> optionalParkingBoy = parkingBoyRepository.findById(user.getId());
        if(!optionalParkingBoy.isPresent()) {
            throw new LoginTokenExpiredException();
        }
        ParkingBoy parkingBoy = optionalParkingBoy.get();
        List<ParkingLot> returnParkingLotList = parkingBoyRepository.findById(user.getId()).get().getParkingLotList();
        if (status == 1 && parkingLotListIsFull(returnParkingLotList)) {
            return parkingOrderList;
        }
        parkingOrderList.addAll(parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy, status));
        return parkingOrderList;
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
                parkingOrder.setParkingLot(addParkingLotAvailable(parkingOrder.getParkingLot().getId()));
                break;
            case 6:
                parkingBoy.setStatus(0);
                break;
            default:
                break;
        }
        parkingBoyRepository.save(parkingBoy);
        parkingOrder.setStatus(status);
        parkingOrder.setParkingBoy(parkingBoy);
        return parkingOrderRepository.save(parkingOrder);
    }

    private ParkingLot addParkingLotAvailable(String id) {
        ParkingLot parkingLot = parkingLotRepository.findById(id).orElse(null);
        parkingLot.setAvailable(parkingLot.getAvailable()+1);
        return parkingLotRepository.save(parkingLot);
    }

    public List<ParkingOrder> findParkingBoyUnfinishedOrders() {
        Integer parkingBoyId = userOperator.getUser().getId();
        Optional<ParkingBoy> optionalParkingBoy = parkingBoyRepository.findById(parkingBoyId);
        if(!optionalParkingBoy.isPresent()) {
            throw new LoginTokenExpiredException();
        }
        ParkingBoy parkingBoy = optionalParkingBoy.get();
        List<ParkingOrder> orders = new ArrayList<>();
        orders.addAll(parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy, 4));
        orders.addAll(parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy, 3));
        return orders;
    }

    public ParkingOrder finishRobOrder(String parkingOrderId, String parkingLotId) {
        User user = userOperator.getUser();
        Integer parkingBoyId = user.getId();
        Optional<ParkingBoy> optionalParkingBoy = parkingBoyRepository.findById(parkingBoyId);
        Optional<ParkingOrder> optionalParkingOrder = parkingOrderRepository.findById(parkingOrderId);
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findById(parkingLotId);
        if (!optionalParkingBoy.isPresent()) {
            throw new ParkingBoyIdErrorException();
        }
        if (!optionalParkingOrder.isPresent()) {
            throw new ParkingOrderIdErrorException();
        }
        if (!optionalParkingLot.isPresent()) {
            throw new ParkingLotIdErrorException();
        }
        ParkingBoy parkingBoy = optionalParkingBoy.get();
        ParkingOrder parkingOrder = optionalParkingOrder.get();
        ParkingLot parkingLot = optionalParkingLot.get();
        parkingBoy.setStatus(ParkingBoyStatus.BUSY);
        parkingLot.setAvailable(parkingLot.getAvailable() - 1);
        parkingOrder.setParkingLot(parkingLot);
        parkingOrder.setParkingBoy(parkingBoy);
        parkingOrder.setStatus(ParkingOrderStatus.RECEIVED_ORDER);
        parkingBoyRepository.save(parkingBoy);
        parkingLotRepository.save(parkingLot);
        return parkingOrderRepository.save(parkingOrder);
    }
}
