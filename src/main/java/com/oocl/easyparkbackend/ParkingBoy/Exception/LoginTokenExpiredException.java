package com.oocl.easyparkbackend.ParkingBoy.Exception;

public class LoginTokenExpiredException extends RuntimeException {
    @Override
    public String getMessage() {
        return "登录过期，请重新登陆！";
    }
}
