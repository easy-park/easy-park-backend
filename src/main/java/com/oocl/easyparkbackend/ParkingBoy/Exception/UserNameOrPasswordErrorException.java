package com.oocl.easyparkbackend.ParkingBoy.Exception;

public class UserNameOrPasswordErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "身份认证失败";
    }
}
