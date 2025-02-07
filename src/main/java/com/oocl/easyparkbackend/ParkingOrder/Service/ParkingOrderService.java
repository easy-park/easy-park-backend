package com.oocl.easyparkbackend.ParkingOrder.Service;

import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.Customer.Exception.NotFindCustomerExcepetion;
import com.oocl.easyparkbackend.Customer.Repository.CustomerRepository;
import com.oocl.easyparkbackend.ParkingBoy.Exception.NotFindParkingBoyException;
import com.oocl.easyparkbackend.ParkingLot.Exception.TypeErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Exception.*;
import com.oocl.easyparkbackend.Util.RedisLock;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.LoginTokenExpiredException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotIdErrorException;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Repository.ParkingOrderRepository;
import com.oocl.easyparkbackend.common.vo.ParkingBoyStatus;
import com.oocl.easyparkbackend.common.vo.ParkingOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

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


    public List<ParkingOrder> findParkingOrderByStatus(int status) {
        User user = userOperator.getUser();
        List<ParkingOrder> parkingOrderList = new ArrayList<>();
        Optional<ParkingBoy> optionalParkingBoy = parkingBoyRepository.findById(user.getId());
        if (!optionalParkingBoy.isPresent()) {
            throw new LoginTokenExpiredException();
        }
        ParkingBoy parkingBoy = optionalParkingBoy.get();
        List<ParkingLot> returnParkingLotList = parkingBoy.getParkingLotList();
        if (status == 1 && parkingLotListIsFull(returnParkingLotList)) {
            return parkingOrderList;
        }
        parkingOrderList.addAll(parkingOrderRepository.findAllByStatus(status));
        parkingOrderList.addAll(parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy, 2));
        return parkingOrderList;
    }

    private boolean parkingLotListIsFull(List<ParkingLot> parkingLotList) {
        return parkingLotList.stream().map(ParkingLot::getAvailable).reduce(0, (a, b) -> a + b) == 0;
    }

    public ParkingOrder updateParkingOrderStatus(String orderId, int status) {
        ParkingOrder parkingOrder = parkingOrderRepository.findById(orderId).orElse(null);
        if (parkingOrder == null) {
            throw new LoginTokenExpiredException();
        }
        ParkingBoy parkingBoy = parkingOrder.getParkingBoy();
        switch (status) {
            case 3:
            case 6:
                parkingOrder.setEndTime(new Timestamp(new Date().getTime()));
                parkingBoy.setStatus(0);
                break;
            case 4:
                double price = calculatePrice(parkingOrder.getStartTime());
                parkingOrder.setPrice(price);
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
        parkingLot.setAvailable(parkingLot.getAvailable() + 1);
        return parkingLotRepository.save(parkingLot);
    }

    public List<ParkingOrder> findParkingBoyUnfinishedOrders() {
        Integer parkingBoyId = userOperator.getUser().getId();
        Optional<ParkingBoy> optionalParkingBoy = parkingBoyRepository.findById(parkingBoyId);
        if (!optionalParkingBoy.isPresent()) {
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
        if (optionalParkingOrder.isPresent()) {
            return optionalParkingOrder.get();
        }
        throw new ParkingOrderIdErrorException();
    }

    public ParkingOrder receiveOrder(String parkingOrderId) {
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(parkingOrderId.toString(), String.valueOf(time))) {
            throw new OrderNotExistException();
        }
        if (parkingOrderRepository.findById(parkingOrderId).get().getStatus() != 1) {
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
        ParkingOrder order = parkingOrderRepository.save(parkingOrder);
        redisLock.unlock(parkingOrderId.toString(), String.valueOf(time));
        return order;
    }

    public ParkingOrder generateParkingOrder(String carNumber) {
        User user = userOperator.getUser();
        Customer customer = customerRepository.findById(user.getId()).orElse(null);
        if (customer != null) {
            if (parkingOrderRepository.findByCarNumberAndEndTime(carNumber, null) == null) {
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
        if (customer != null) {
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

    public List<ParkingOrder> getAllParkingOrder() {
        List<ParkingOrder> parkingOrderList = new ArrayList<>();
        parkingOrderList.addAll(parkingOrderRepository.findAll());
        return parkingOrderList;
    }

    public List<ParkingOrder> searchParkingOrdersByCarNumber(String carNumber) {
        List<ParkingOrder> parkingOrders = new ArrayList<>();
        parkingOrders.addAll(parkingOrderRepository.findByCarNumberLike("%" + carNumber + "%"));
        return parkingOrders;
    }

    public List<ParkingOrder> getParkingOrderByType(String types) {
        if (types.equals("存车")) {
            List<ParkingOrder> parkingOrders = new ArrayList<>();
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(1));
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(2));
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(3));
            return parkingOrders;
        } else if (types.equals("取车")) {
            List<ParkingOrder> parkingOrders = new ArrayList<>();
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(4));
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(5));
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(6));
            return parkingOrders;
        }
        throw new TypeErrorException();

    }

    public List<ParkingOrder> getParkingOrderByStatus(String status) {
        if (status.equals("无人处理")) {
            List<ParkingOrder> parkingOrders = new ArrayList<>();
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(1));
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(4));
            return parkingOrders;
        } else if (status.equals("已完成")) {
            List<ParkingOrder> parkingOrders = new ArrayList<>();
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(6));
            return parkingOrders;
        } else if (status.equals("存取中")) {
            List<ParkingOrder> parkingOrders = new ArrayList<>();
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(2));
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(3));
            parkingOrders.addAll(parkingOrderRepository.findAllByStatus(5));
            return parkingOrders;
        }
        throw new StatusErrorException();
    }

    public ParkingOrder assignParkingBoy(String parkingOrderId, int parkingBoyId) {
        Optional<ParkingOrder> optionalParkingOrder = parkingOrderRepository.findById(parkingOrderId);
        Optional<ParkingBoy> optionalParkingBoy = parkingBoyRepository.findById(parkingBoyId);
        if (!optionalParkingBoy.isPresent()) {
            throw new ParkingBoyIdErrorException();
        }
        if (!optionalParkingOrder.isPresent()) {
            throw new OrderNotExistException();
        }
        if (optionalParkingOrder.get().getParkingBoy() != null) {
            throw new OrderRobedException();
        }
        ParkingOrder parkingOrder = optionalParkingOrder.get();
        parkingOrder.setParkingBoy(optionalParkingBoy.get());
        parkingOrder.setStatus(2);
        return parkingOrderRepository.save(parkingOrder);
    }

    public List<String> getUnrepeatOrders() {
        User user = userOperator.getUser();
        Optional<Customer> optionalCustomer = customerRepository.findById(user.getId());
        if (!optionalCustomer.isPresent()) {
            throw new NotFindCustomerExcepetion();
        }
        List<String> historyCarNumber = new ArrayList<>();
        List<ParkingOrder> parkingOrders = parkingOrderRepository.findAllByCustomer(optionalCustomer.get());
        for (ParkingOrder parkingOrder : parkingOrders
        ) {
            if (!historyCarNumber.contains(parkingOrder.getCarNumber())) {
                historyCarNumber.add(parkingOrder.getCarNumber());
            }
        }

        return historyCarNumber;
    }

    private double calculatePrice(Timestamp timestamp) {
        LocalDateTime start = timestamp.toLocalDateTime();
        LocalDateTime end = LocalDateTime.now();

        int num[] = {35, 56, 30};
        int timeNum[] = {420, 420, 600};
        int moneyNum[] = {5, 8, 3};
        int index;
        int minute = start.getMinute();
        int endmin = end.getMinute();
        double money = 0;
        Duration duration = Duration.between(start, end);
        long time = duration.toMinutes();
        if (time < 420) {
            int shour = start.getHour();
            int ehour = end.getHour();
            int sindex = 0;
            int eindex = 0;
            sindex = getindex(shour);
            eindex = getindex(ehour);
            if(sindex==eindex){
                money = duration.toMinutes()/60.0*moneyNum[sindex];
            }else if (sindex ==0){
                money = (17 - shour) * 60 - minute;
                time -=money;
                money = money / 60.0 * 5;
            }else if(sindex ==1){
                money = (24 - shour) * 60 - minute;
                time -=money;
                money = money / 60.0 * 8;
            }else{
                money = (10 - shour) * 60 - minute;
                time -=money;
                money = money / 60.0 * 3;
            }
            money += (double)time / 60.0 * moneyNum[eindex];
        } else {
            int hour = start.getHour();
            if (hour >= 10 && hour < 17) {
                index = 1;
                money = (17 - hour) * 60 - minute;
                time -=money;
                money = money / 60.0 * 5;
            } else if (hour >= 17 && hour < 24) {
                index = 2;
                money = (24 - hour) * 60 - minute;
                time -=money;
                money = money / 60.0 * 8;
            } else {
                index = 0;
                money = (10 - hour) * 60 - minute;
                time -=money;
                money = money / 60.0 * 3;

            }
            while (time > timeNum[index]) {
                time = time - timeNum[index];
                money += num[index];
                index++;
                if (index == 3) {
                    index -= 3;
                }
            }
            money += (double)time / 60.0 * moneyNum[index];
        }
        BigDecimal bg = new BigDecimal(money);
        return bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private static int getindex(int hour) {
        int index;
        if(hour>=10&&hour<17){
            index = 0;
        }else if(hour>=17&&hour<=24){
            index = 1;
        }else {
            index = 2;
        }
        return index;
    }

    public List<ParkingOrder> findAllByParkingBoyAndStatus(int status) {
        User user = userOperator.getUser();
        ParkingBoy parkingBoy = parkingBoyRepository.findById(user.getId()).orElse(null);
        if(parkingBoy == null){
            throw new ParkingBoyIdErrorException();
        }
        return parkingOrderRepository.findAllByParkingBoyAndStatus(parkingBoy,status);
    }
}
