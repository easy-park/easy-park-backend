package com.oocl.easyparkbackend.ParkingOrder.Service;

import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.Customer.Exception.NotFindCustomerExcepetion;
import com.oocl.easyparkbackend.Customer.Repository.CustomerRepository;
import com.oocl.easyparkbackend.ParkingOrder.Exception.OrderNotExistException;
import com.oocl.easyparkbackend.Util.RedisLock;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.LoginTokenExpiredException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotIdErrorException;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Exception.AlreadyParkingException;
import com.oocl.easyparkbackend.ParkingOrder.Exception.ParkingOrderIdErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Repository.ParkingOrderRepository;
import com.oocl.easyparkbackend.common.vo.ParkingBoyStatus;
import com.oocl.easyparkbackend.common.vo.ParkingOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingOrderService {

    @Autowired
    private RedisLock redisLock;

    private static final int TIMEOUT = 10 * 1000;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
        List<ParkingLot> returnParkingLotList = parkingBoy.getParkingLotList();
        if (status == 1 && parkingLotListIsFull(returnParkingLotList)) {
            return parkingOrderList;
        }
        parkingOrderList.addAll(parkingOrderRepository.findAllByStatus( status));
        return parkingOrderList;
    }

    private boolean parkingLotListIsFull(List<ParkingLot> parkingLotList) {
        return parkingLotList.stream().map(ParkingLot::getAvailable).reduce(0,(a,b) -> a + b)==0;
    }

    public ParkingOrder updateParkingOrderStatus(String orderId,int status) {
        ParkingOrder parkingOrder = parkingOrderRepository.findById(orderId).orElse(null);
        if (parkingOrder == null){
            throw new LoginTokenExpiredException();
        }
        ParkingBoy parkingBoy = parkingOrder.getParkingBoy();
        switch (status){
            case 3:
            case 6:
                parkingBoy.setStatus(0);
                break;
            case 5:
                parkingBoy.setStatus(1);
                parkingOrder.setParkingLot(addParkingLotAvailable(parkingOrder.getParkingLot().getId()));
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
        Optional<ParkingOrder> optionalParkingOrder = parkingOrderRepository.findById(parkingOrderId);
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findById(parkingLotId);
        if (!optionalParkingLot.isPresent()) {
            throw new ParkingLotIdErrorException();
        }
        ParkingOrder parkingOrder = optionalParkingOrder.get();
        ParkingLot parkingLot = optionalParkingLot.get();
        parkingLot.setAvailable(parkingLot.getAvailable() - 1);
        parkingOrder.setParkingLot(parkingLot);
        parkingLotRepository.save(parkingLot);
        return parkingOrderRepository.save(parkingOrder);
    }

    public ParkingOrder getOrderById(String id) {
        Optional<ParkingOrder> optionalParkingOrder = parkingOrderRepository.findById(id);
        if(optionalParkingOrder.isPresent()) {
            return optionalParkingOrder.get();
        }
        throw new ParkingOrderIdErrorException();
    }

    public ParkingOrder receiveOrder(String parkingOrderId) {
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(parkingOrderId.toString(), String.valueOf(time))) {
            throw new OrderNotExistException();
        }
        if(parkingOrderRepository.findById(parkingOrderId).get().getStatus() != 1){
            throw new OrderNotExistException();
        }
        User user = userOperator.getUser();
        Integer parkingBoyId = user.getId();
        Optional<ParkingBoy> optionalParkingBoy = parkingBoyRepository.findById(parkingBoyId);
        Optional<ParkingOrder> optionalParkingOrder = parkingOrderRepository.findById(parkingOrderId);
        if (!optionalParkingBoy.isPresent()) {
            throw new ParkingBoyIdErrorException();
        }
        if (!optionalParkingOrder.isPresent()) {
            throw new ParkingOrderIdErrorException();
        }
        ParkingBoy parkingBoy = optionalParkingBoy.get();
        ParkingOrder parkingOrder = optionalParkingOrder.get();
        parkingBoy.setStatus(ParkingBoyStatus.BUSY);
        parkingOrder.setStatus(ParkingOrderStatus.RECEIVED_ORDER);
        parkingOrder.setParkingBoy(parkingBoy);
        parkingBoyRepository.save(parkingBoy);
        redisLock.unlock(parkingOrderId.toString(), String.valueOf(time));
        return parkingOrderRepository.save(parkingOrder);
    }

    public ParkingOrder generateParkingOrder(String carNumber) {
        User user = userOperator.getUser();
        Customer customer = customerRepository.findById(user.getId()).orElse(null);
        if (customer != null ){
            if (parkingOrderRepository.findByCarNumberAndEndTime(carNumber,null) == null){
                ParkingOrder parkingOrder = new ParkingOrder();
                parkingOrder.setCarNumber(carNumber);
                parkingOrder.setCustomer(customer);
                parkingOrder.setStartTime(new Timestamp(System.currentTimeMillis()));
                parkingOrder.setStatus(1);
                return parkingOrderRepository.save(parkingOrder);
            }
            throw new AlreadyParkingException();

        }
        throw new NotFindCustomerExcepetion();
    }

    public List<ParkingOrder> getNotFinishParkingOrderByUser() {
        Customer customer = customerRepository.findById(userOperator.getUser().getId()).orElse(null);
        if (customer != null ) {
            List<ParkingOrder> statusOne = parkingOrderRepository.findAllByCustomerAndStatus(customer, 1);
            List<ParkingOrder> statusTwo = parkingOrderRepository.findAllByCustomerAndStatus(customer, 2);
            List<ParkingOrder> statusThree = parkingOrderRepository.findAllByCustomerAndStatus(customer, 3);
            List<ParkingOrder> statusFour = parkingOrderRepository.findAllByCustomerAndStatus(customer, 4);
            List<ParkingOrder> statusFive = parkingOrderRepository.findAllByCustomerAndStatus(customer, 5);
            List<ParkingOrder> allList = new ArrayList<>();
            allList.addAll(statusOne);
            allList.addAll(statusTwo);
            allList.addAll(statusThree);
            allList.addAll(statusFour);
            allList.addAll(statusFive);
            return allList;
        }
        return null;
    }
}
