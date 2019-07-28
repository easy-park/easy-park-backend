package com.oocl.easyparkbackend.ParkingOrder.Controller;

import com.oocl.easyparkbackend.ParkingBoy.Exception.LoginTokenExpiredException;
import com.oocl.easyparkbackend.ParkingOrder.Service.ParkingOrderService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class ParkingOrderController {

    @Autowired
    private ParkingOrderService parkingOrderService;

    @GetMapping(path = "/parkingOrders",params = "status")
    public ResponseVO findParkingOrderByStatus(@RequestParam int status){
        return ResponseVO.success(parkingOrderService.findParkingOrderByStatus(status));
    }

    @PutMapping(path = "/parkingOrders/{orderId}",params = "status")
    public ResponseVO updateParkingOrderStatus(@PathVariable String orderId ,@RequestParam int status){
        return ResponseVO.success(parkingOrderService.updateParkingOrderStatus(orderId,status));
    }

    @GetMapping(path = "/parkingOrders")
    public ResponseVO findParkingBoyUnfinishedOrders() {
        return ResponseVO.success(parkingOrderService.findParkingBoyUnfinishedOrders());
    }

    @ExceptionHandler(LoginTokenExpiredException.class)
    public ResponseVO handleUserNameOrPasswordErrorException(LoginTokenExpiredException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }
}
