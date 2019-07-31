package com.oocl.easyparkbackend.Customer.Exception;

public class RegisterFailedException extends RuntimeException {
    @Override
    public String getMessage(){
        return "注册失败";
    }
}
