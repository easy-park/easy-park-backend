package com.oocl.easyparkbackend.ExceptionHandler;

import com.itmuch.lightsecurity.exception.LightSecurityException;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class LightSecurityExceptionHandler {
    @ExceptionHandler(value = {LightSecurityException.class})
    @ResponseBody
    public ResponseVO error(LightSecurityException exception) {
        return ResponseVO.serviceFail(exception.getMessage());
    }
}
