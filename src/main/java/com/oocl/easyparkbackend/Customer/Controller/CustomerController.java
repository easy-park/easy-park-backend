package com.oocl.easyparkbackend.Customer.Controller;

import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.Customer.Service.CustomerService;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Customer customerSave = customerService.save(customer);
        customerSave.setPassword(null);
        return ResponseVO.success(customerSave);
    }

    @GetMapping()
    public ResponseVO getUserInfo(){
        Customer customer = customerService.findById();
        customer.setPassword(null);
        return ResponseVO.success(customer);
    }

    @ExceptionHandler(UserNameOrPasswordErrorException.class)
    public ResponseVO handleUserNameOrPasswordErrorException(UserNameOrPasswordErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

}
