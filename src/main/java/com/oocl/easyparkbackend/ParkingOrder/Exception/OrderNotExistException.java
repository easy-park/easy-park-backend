package com.oocl.easyparkbackend.ParkingOrder.Exception;

public class OrderNotExistException extends RuntimeException {
    @Override
    public String getMessage() {
        return "很遗憾，你没抢到单";
    }
}
