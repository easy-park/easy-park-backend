package com.oocl.easyparkbackend.Manage.Controller;

import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Exception.FrozenManagerException;
import com.oocl.easyparkbackend.Manage.Exception.NotFindManagerException;
import com.oocl.easyparkbackend.Manage.Service.ManageService;
import com.oocl.easyparkbackend.Manage.Vo.BoysLotVo;
import com.oocl.easyparkbackend.Manage.Vo.ManageVo;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.authentication.AuthenticateException;
import com.oocl.easyparkbackend.base.BaseException;
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

    @PostMapping("/login")
    public ResponseVO login(@RequestBody Manage manage) {
        return ResponseVO.successToken(manageService.login(manage));
    }

    @GetMapping
    public ResponseVO getInfo(){
        Manage manager = manageService.getManager();
        return ResponseVO.success(new ManageVo(manager));
    }

    @ExceptionHandler(AuthenticateException.class)
    public ResponseVO handleUserNameOrPasswordErrorException(BaseException exception) {
        return ResponseVO.serviceFail(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(NotFindManagerException.class)
    public ResponseVO handleNotFindManagerException(BaseException exception) {
        return ResponseVO.serviceFail(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(FrozenManagerException.class)
    public ResponseVO handleFrozenManagerException(BaseException exception){
        return ResponseVO.serviceFail(exception.getStatus(),exception.getMessage());
    }
}
