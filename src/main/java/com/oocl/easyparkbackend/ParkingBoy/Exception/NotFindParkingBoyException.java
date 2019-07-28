package com.oocl.easyparkbackend.ParkingBoy.Exception;

public class NotFindParkingBoyException extends RuntimeException {

    @Override
    public String getMessage(){
        return "不存在该停车员信息";
    }
}
