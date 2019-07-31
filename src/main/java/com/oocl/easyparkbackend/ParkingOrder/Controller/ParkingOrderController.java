package com.oocl.easyparkbackend.ParkingOrder.Controller;


import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.ParkingBoy.Exception.LoginTokenExpiredException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotIdErrorException;
import com.oocl.easyparkbackend.ParkingLot.Exception.TypeErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Exception.*;

import com.oocl.easyparkbackend.ParkingOrder.Service.ParkingOrderService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @GetMapping(path = "/parkingOrders", params = "status")
    public ResponseVO findParkingOrderByStatus(@RequestParam int status) {
        return ResponseVO.success(parkingOrderService.findParkingOrderByStatus(status));
    }

    @PutMapping(path = "/parkingOrders/{orderId}", params = "status")
    public ResponseVO updateParkingOrderStatus(@PathVariable String orderId, @RequestParam int status) {
        return ResponseVO.success(parkingOrderService.updateParkingOrderStatus(orderId, status));
    }

    @GetMapping(path = "/parkingOrders")
    public ResponseVO findParkingBoyUnfinishedOrders() {
        return ResponseVO.success(parkingOrderService.findParkingBoyUnfinishedOrders());
    }

    @GetMapping(path = "/parkingOrders", params = {"parkingOrderId", "parkingLotId"})
    public ResponseVO finishRobOrder(@RequestParam String parkingOrderId, @RequestParam String parkingLotId) {
        return ResponseVO.success(parkingOrderService.finishRobOrder(parkingOrderId, parkingLotId));
    }

    @PutMapping(path = "/parkingOrders")
    public ResponseVO receiveOrder(@RequestBody ParkingOrder order) {
        ParkingOrder parkingOrder = parkingOrderService.receiveOrder(order.getId());
        return ResponseVO.success(parkingOrder);
    }

    @GetMapping("/parkingOrders/{id}")
    public ResponseVO getParkingOrder(@PathVariable String id) {
        return ResponseVO.success(parkingOrderService.getOrderById(id));
    }

    @PostMapping(value = "/parkingOrders", params = "carNumber")
    public ResponseVO generateParkingOrder(@RequestParam String carNumber) {
        ParkingOrder parkingOrder = parkingOrderService.generateParkingOrder(carNumber);
        return ResponseVO.success(parkingOrder);
    }

    @GetMapping("/parking_orders_customer")
    public ResponseVO getParkingOrderByCustomer() {
        List<ParkingOrder> list = parkingOrderService.getNotFinishParkingOrderByUser();
        return ResponseVO.success(list);
    }

    @GetMapping("/parkingorderlist")
    public ResponseVO getAllParkingOrder() {
        List<ParkingOrder> list = parkingOrderService.getAllParkingOrder();
        return ResponseVO.success(list);
    }

    @GetMapping(path = "/parkingorderlist", params = "carnumber")
    public ResponseVO searchParkingOrdersByName(String carnumber) {
        List<ParkingOrder> list = parkingOrderService.searchParkingOrdersByCarNumber(carnumber);
        return ResponseVO.success(list);
    }

    @GetMapping(path = "/parkingorderlist", params = "type")
    public ResponseVO getSaveParkingOrder(String type) {
        List<ParkingOrder> list = parkingOrderService.getParkingOrderByType(type);
        return ResponseVO.success(list);
    }

    @GetMapping(path = "/parkingorderlist", params = "status")
    public ResponseVO getParkingOrdersByStatus(String status) {
        List<ParkingOrder> list = parkingOrderService.getParkingOrderByStatus(status);
        return ResponseVO.success(list);
    }

    @GetMapping(path = "/parkingorderlist", params = {"parkingOrderId","parkingBoyId"})
    public ResponseVO assignParkingBoy(String parkingOrderId, int parkingBoyId) {
        ParkingOrder parkingOrder = parkingOrderService.assignParkingBoy(parkingOrderId,parkingBoyId);
        return ResponseVO.success(parkingOrder);
    }

    @GetMapping(path = "/unrepeatcarnumbers")
    public ResponseVO getUnrepeatCarNumbers() {
        List<String> historyCarNumber = parkingOrderService.getUnrepeatOrders();
        return ResponseVO.success(historyCarNumber);
    }

    @ExceptionHandler(AlreadyParkingException.class)
    public ResponseVO handleAlreadyParkingException(AlreadyParkingException exception) {
        return ResponseVO.serviceFail(600,exception.getMessage());
    }


    @ExceptionHandler(LoginTokenExpiredException.class)
    public ResponseVO handleUserNameOrPasswordErrorException(LoginTokenExpiredException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(ParkingBoyIdErrorException.class)
    public ResponseVO handleParkingBoyIdErrorException(ParkingBoyIdErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(ParkingOrderIdErrorException.class)
    public ResponseVO handleParkingOrderIdErrorException(ParkingOrderIdErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(ParkingLotIdErrorException.class)
    public ResponseVO handleParkingLotIdErrorException(ParkingLotIdErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(OrderNotExistException.class)
    public ResponseVO handleUserOrderNotExistException(OrderNotExistException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(TypeErrorException.class)
    public ResponseVO handleTypeErrorException(TypeErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(StatusErrorException.class)
    public ResponseVO handleStatusErrorException(StatusErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(OrderRobedException.class)
    public ResponseVO handleOrderRobedException(OrderRobedException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

}
