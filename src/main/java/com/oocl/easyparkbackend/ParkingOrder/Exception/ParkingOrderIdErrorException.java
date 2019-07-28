package com.oocl.easyparkbackend.ParkingOrder.Exception;

public class ParkingOrderIdErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "ParkingOrderId Error";
    }
}
