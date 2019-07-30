package com.oocl.easyparkbackend.Manage.Controller;

import com.oocl.easyparkbackend.Manage.Service.ManageService;
import com.oocl.easyparkbackend.Manage.Vo.BoysLotVo;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManageController {

    @Autowired
    private ManageService manageService;


    @PostMapping("/parkingBoy")
    public ResponseVO setParkingBoysParkingLots(@RequestBody BoysLotVo vo){
        return ResponseVO.success(manageService.setParkingBoysParkingLots(vo));
    }

    @PutMapping("/parkingBoy")
    public ResponseVO changeParkingBoysParkingLots(@RequestBody BoysLotVo vo){
        return ResponseVO.success(manageService.changeParkingBoysParkingLots(vo));
    }
}
