package com.oocl.easyparkbackend.ParkingLot.Controller;

import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLotDashboradVO;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotNameAndCapacityNotNull;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotRangeErrorException;
import com.oocl.easyparkbackend.ParkingLot.Service.ParkingLotService;
import com.oocl.easyparkbackend.ParkingLot.Vo.LotsAndBoysLotsVo;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/parkingLots")
    public ResponseVO getParkingLotByParkingBoy() {
        return ResponseVO.success(parkingLotService.getParkingLotByParkingBoy());
    }

    @GetMapping("/parking_lots")
    public ResponseVO getAllParkingLot() {
        List<ParkingLot> parkingLots = parkingLotService.getAllParkingLot();
        return ResponseVO.success(parkingLots);
    }

    @GetMapping(path = "/parking_lots", params = "name")
    public ResponseVO searchParkingLotsByName(String name) {
        List<ParkingLot> parkingLots = parkingLotService.findParkingLotsByName(name);
        return ResponseVO.success(parkingLots);
    }

    @GetMapping(path = "/parking_lots", params = {"start", "end"})
    public ResponseVO getParkingLotsByRange(int start, int end) {
        List<ParkingLot> parkingLots =  parkingLotService.getParkingLotsByRange(start, end);
        return ResponseVO.success(parkingLots);
    }

    @GetMapping(path = "/parking_lots_boys",params = "parkingBoyId")
    public ResponseVO getAllParkingListAndParkingBoysParkingLot(Integer parkingBoyId) {
        LotsAndBoysLotsVo lotsAndBoysLotsVo = parkingLotService.getAllParkingListAndParkingBoysParkingLot(parkingBoyId);
        return ResponseVO.success(lotsAndBoysLotsVo);
    }
    
    @PutMapping("/parking_lots")
    public ResponseVO updateParkingLot(@RequestBody ParkingLot parkingLot) {
        ParkingLot fetchedParkingLot = parkingLotService.update(parkingLot);
        return ResponseVO.success(fetchedParkingLot);
    }


    @PostMapping("/parking_lots")
    public ResponseVO addNewParkingLot(@RequestBody ParkingLot parkingLot){
        ParkingLot parkingLot1 = parkingLotService.addParkingLot(parkingLot);
        return ResponseVO.success(parkingLot1);
    }

    @GetMapping("/parkingDashboard")
    public ResponseVO ShowParkingLotDashboard(){
        List<ParkingLotDashboradVO> parkingLotDashboradVOList = parkingLotService.getParkingLotDashboard();
        return ResponseVO.success(parkingLotDashboradVOList);
    }


    @ExceptionHandler(ParkingBoyIdErrorException.class)
    public ResponseVO handleParkingBoyIdErrorException(ParkingBoyIdErrorException exception){
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(ParkingLotNameAndCapacityNotNull.class)
    public ResponseVO handleParkingLotNameAndCapacityNotNull(ParkingLotNameAndCapacityNotNull exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

    @ExceptionHandler(ParkingLotRangeErrorException.class)
    public ResponseVO handleParkingLotRangeErrorException(ParkingLotRangeErrorException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }

}
