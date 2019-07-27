package com.oocl.easyparkbackend.ParkingBoy.Controller;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingBoys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService parkingBoyService;

    @PostMapping
    public ResponseVO login(@RequestBody ParkingBoy parkingBoy) {
        return ResponseVO.success(parkingBoyService.login(parkingBoy));
    }

    @ExceptionHandler(UserNameOrPasswordErrorException.class)
    public ResponseVO handleUserNameOrPasswordErrorException(UserNameOrPasswordErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }
}
