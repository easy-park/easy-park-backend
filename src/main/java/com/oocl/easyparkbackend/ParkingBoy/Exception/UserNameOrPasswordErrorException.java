package com.oocl.easyparkbackend.ParkingBoy.Exception;

public class UserNameOrPasswordErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "用户名或密码错误！";
    }
}
