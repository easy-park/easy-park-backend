package com.oocl.easyparkbackend.ParkingOrder.Exception;

public class OrderRobedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Order has been robed !";
    }
}
