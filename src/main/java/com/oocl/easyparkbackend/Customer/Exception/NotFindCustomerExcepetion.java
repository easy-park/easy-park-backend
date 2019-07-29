package com.oocl.easyparkbackend.Customer.Exception;

public class NotFindCustomerExcepetion extends RuntimeException {
    @Override
    public String getMessage(){
        return "不存在该客户";
    }
}
