package com.oocl.easyparkbackend.ParkingOrder.Exception;

public class AlreadyParkingException extends RuntimeException {
    @Override
    public String getMessage() {
        return "此车已进入订单";
    }
}
