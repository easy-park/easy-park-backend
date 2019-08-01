package com.oocl.easyparkbackend.Employee.Exception;

public class NotAllowAdjustException extends RuntimeException {
    @Override
    public String getMessage() {
        return "还有未完成订单，不能更改职位";
    }
}
