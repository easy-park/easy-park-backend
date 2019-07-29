package com.oocl.easyparkbackend.ParkingLot.Controller;

import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Service.ParkingLotService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/parkingLots")
    public ResponseVO getParkingLotByParkingBoy() {
        return ResponseVO.success(parkingLotService.getParkingLotByParkingBoy());
    }

    @GetMapping("/parkinglots")
    public ResponseVO getAllParkingLot() {
        List<ParkingLot> parkingLots = parkingLotService.getAllParkingLot();
        return ResponseVO.success(parkingLots);
    }

    @ExceptionHandler(ParkingBoyIdErrorException.class)
    public ResponseVO handleParkingBoyIdErrorException(ParkingBoyIdErrorException exception){
        return ResponseVO.serviceFail(exception.getMessage());
    }
}
