package com.oocl.easyparkbackend.Employee.Controller;

import com.oocl.easyparkbackend.Employee.Entity.Clerk;
import com.oocl.easyparkbackend.Employee.Entity.Employee;
import com.oocl.easyparkbackend.Employee.Exception.NotAllowAdjustException;
import com.oocl.easyparkbackend.Employee.Service.EmployeeService;
import com.oocl.easyparkbackend.Employee.Vo.AdjustVo;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

import static com.oocl.easyparkbackend.common.vo.ResponseVO.serviceFail;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseVO addEmployee(@RequestBody Employee employee){
        try{
            return ResponseVO.success(employeeService.addEmployee(employee));
        }catch (ConstraintViolationException e){
            return serviceFail("员工信息不能为空");
        }
    }

    @PutMapping()
    public ResponseVO adjustPosition(@RequestBody AdjustVo adjustVO){
        Clerk clerk = employeeService.adjustPosition(adjustVO);
        return ResponseVO.success(clerk);
    }

    @ExceptionHandler(NotAllowAdjustException.class)
    public ResponseVO handleNotAllowAdjustException(NotAllowAdjustException exception){
        return ResponseVO.serviceFail(444,exception.getMessage());
    }
}
