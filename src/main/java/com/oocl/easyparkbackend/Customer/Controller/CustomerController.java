package com.oocl.easyparkbackend.Customer.Controller;

import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.Customer.Exception.RegisterFailedException;
import com.oocl.easyparkbackend.Customer.Service.CustomerService;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody Customer customer){
        String token = customerService.login(customer);
        return ResponseVO.successToken(token);
    }

    @PostMapping("/register")
    public ResponseVO register(@RequestBody Customer customer){
        String token  = customerService.save(customer);
        return ResponseVO.successToken(token);
    }

    @GetMapping()
    public ResponseVO getUserInfo(){
        Customer customer = customerService.findById();
        customer.setPassword(null);
        return ResponseVO.success(customer);
    }

    @GetMapping("/historyorder")
    public ResponseVO getHistoryOrder() {
        List<ParkingOrder> parkingOrders = customerService.getHistoryOrder();
        return ResponseVO.success(parkingOrders);
    }

    @ExceptionHandler(UserNameOrPasswordErrorException.class)
    public ResponseVO handleUserNameOrPasswordErrorException(UserNameOrPasswordErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(RegisterFailedException.class)
    public ResponseVO registerErrorException(RegisterFailedException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

}
