package com.oocl.easyparkbackend.ParkingBoy.Controller;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.FrozenParkingBoyException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.NotFindParkingBoyException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import com.oocl.easyparkbackend.exception.BaseResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingBoys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService parkingBoyService;

    @PostMapping
    public ResponseVO login(@RequestBody ParkingBoy parkingBoy) {
        return ResponseVO.successToken(parkingBoyService.login(parkingBoy));
    }

    @GetMapping
    public ResponseVO findParkingBoy() {
        return ResponseVO.success(parkingBoyService.findParkingBoy());
    }

    @GetMapping("/all")
    public ResponseVO getAllParkingBoy() {
        List<ParkingBoy> parkingBoys = parkingBoyService.getAllParkingBoy();
        return ResponseVO.success(parkingBoys);
    }

    @GetMapping(path = "/list", params = "name")
    public ResponseVO findParkingBoysByName(@RequestParam String name) {
        List<ParkingBoy> parkingBoys = parkingBoyService.findParkingBoysByName(name);
        return ResponseVO.success(parkingBoys);
    }

    @GetMapping(path = "/list", params = "phoneNumber")
    public ResponseVO findParkingBoysByPhoneNumber(@RequestParam String phoneNumber) {
        List<ParkingBoy> parkingBoys = parkingBoyService.findParkingBoysByPhoneNumber(phoneNumber);
        return ResponseVO.success(parkingBoys);
    }

    @GetMapping(path = "/one", params = "parkingBoyId")
    public ResponseVO findParkingBoyById(int parkingBoyId) {
        ParkingBoy parkingBoy = parkingBoyService.findParkingBoyById(parkingBoyId);
        return ResponseVO.success(parkingBoy);
    }

    @GetMapping(path = "/parkingLots")
    public ResponseVO findParkingLotsByParkingBoyId(@RequestBody ParkingBoy parkingBoy) {
        List<ParkingLot> parkingLotList = parkingBoyService.findParkingLotList(parkingBoy.getId());
        return ResponseVO.success(parkingLotList);
    }

    @ExceptionHandler(UserNameOrPasswordErrorException.class)
    public ResponseVO handleUserNameOrPasswordErrorException(UserNameOrPasswordErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(ParkingBoyIdErrorException.class)
    public ResponseVO handleParkingBoyIdErrorException(ParkingBoyIdErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(NotFindParkingBoyException.class)
    public ResponseVO handleNotFindParkingBoyException(NotFindParkingBoyException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(FrozenParkingBoyException.class)
    public ResponseVO handleFrozenParkingBoyException(BaseResponseException exception) {
        return ResponseVO.serviceFail(exception.getStatus(), exception.getMessage());
    }
}
