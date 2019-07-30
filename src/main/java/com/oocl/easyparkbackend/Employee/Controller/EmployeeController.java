package com.oocl.easyparkbackend.Employee.Controller;

import com.oocl.easyparkbackend.Employee.Entity.Employee;
import com.oocl.easyparkbackend.Employee.Service.EmployeeService;
import com.oocl.easyparkbackend.common.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;


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
            return ResponseVO.serviceFail("员工信息不能为空");
        }
    }
}
