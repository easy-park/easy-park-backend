package com.oocl.easyparkbackend.ParkingOrder.Controller;


import com.oocl.easyparkbackend.ParkingBoy.Exception.LoginTokenExpiredException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotIdErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Exception.ParkingOrderIdErrorException;

import com.oocl.easyparkbackend.ParkingOrder.Service.ParkingOrderService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(LoginTokenExpiredException.class)
    public ResponseVO handleUserNameOrPasswordErrorException(LoginTokenExpiredException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
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

}
