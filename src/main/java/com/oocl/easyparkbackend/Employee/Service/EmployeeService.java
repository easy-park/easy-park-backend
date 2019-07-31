package com.oocl.easyparkbackend.Employee.Service;


import com.oocl.easyparkbackend.Employee.Entity.Clerk;
import com.oocl.easyparkbackend.Employee.Entity.Employee;
import com.oocl.easyparkbackend.Employee.Exception.NotAllowAdjustException;
import com.oocl.easyparkbackend.Employee.Repository.EmployeeRepository;
import com.oocl.easyparkbackend.Employee.Vo.AdjustVo;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Repository.ManageRepository;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final static String MANAGE_POSITION = "Manage";
    private final static String ADMIN_POSITION = "Admin";
    private final static String EMPLOYEE_POSITION = "Employee";
    private final static String BOY_POSITION = "ParkingBoy";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManageRepository manageRepository;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    public Employee addEmployee(@Valid Employee employee) {
        employee.setPassword("12345");
        return employeeRepository.save(employee);
    }

    public Clerk adjustPosition(AdjustVo adjustVo) throws NotAllowAdjustException {
        String position = adjustVo.getOriginPosition();
        if(!checkIfCanAdjust(adjustVo.getId())){
            throw new NotAllowAdjustException();
        }
        Clerk clerk = new Clerk();
        if(position.equalsIgnoreCase(EMPLOYEE_POSITION)){
            clerk = employeeRepository.findById(adjustVo.getId()).orElse(null);
        }else if(position.equalsIgnoreCase(ADMIN_POSITION) || position.equalsIgnoreCase(MANAGE_POSITION)){
            clerk = manageRepository.findById(adjustVo.getId()).orElse(null);
        }else {
            clerk = parkingBoyRepository.findById(adjustVo.getId()).orElse(null);
        }
        if(clerk != null){
            clerk = saveToNewPosition(adjustVo.getNewPosition(),adjustVo.getOriginPosition(),clerk);
        }
        return clerk;
    }

    public Clerk saveToNewPosition(String newPosition, String originPosition, Clerk clerk){
        if(newPosition.equalsIgnoreCase(EMPLOYEE_POSITION)){
            Employee employee = employeeRepository.findById(clerk.getId()).orElse(null);
            clerk = employeeRepository.save(employee);
            deleteFromOriginPosition(clerk.getId(),originPosition);
        }else if(newPosition.equalsIgnoreCase(ADMIN_POSITION) || newPosition.equalsIgnoreCase(MANAGE_POSITION)){
            Manage manage = manageRepository.findById(clerk.getId()).orElse(null);
            if(originPosition.equalsIgnoreCase(ADMIN_POSITION)){
                manage.setStatus(50);
            }else if(originPosition.equalsIgnoreCase(MANAGE_POSITION)){
                manage.setStatus(0);
            }
            clerk = manageRepository.save(manage);
        }else {
            ParkingBoy parkingBoy = parkingBoyRepository.findById(clerk.getId()).orElse(null);
            clerk = parkingBoyRepository.save(parkingBoy);
            deleteFromOriginPosition(clerk.getId(),originPosition);
        }
        return clerk;
    }

    public void deleteFromOriginPosition(int id, String originPosition){
        if(originPosition.equalsIgnoreCase(EMPLOYEE_POSITION)){
            employeeRepository.deleteById(id);
        }else if(originPosition.equalsIgnoreCase(ADMIN_POSITION) || originPosition.equalsIgnoreCase(MANAGE_POSITION)){
            manageRepository.deleteById(id);
        }else {
            parkingBoyRepository.deleteById(id);
        }
    }

    public boolean checkIfCanAdjust(int boyId){
        ParkingBoy parkingBoy = parkingBoyRepository.findById(boyId).orElse(null);
        List<ParkingOrder> parkingOrders = parkingOrderRepository.findAllByParkingBoyAndStatusIsLessThan(parkingBoy, 6);
        return parkingOrders.size() == 0;
    }
}
