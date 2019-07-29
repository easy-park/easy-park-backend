package com.oocl.easyparkbackend.ParkingOrder.Exception;

public class OrderNotExistException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Order not exist";
    }
}
