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
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final static String MANAGE_POSITION = "Manage";
    private final static String ADMIN_POSITION = "Admin";
    private final static String EMPLOYEE_POSITION = "Employee";
    private final static String BOY_POSITION = "ParkingBoy";
    private final static String DEFAULT_PWD = "12345";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManageRepository manageRepository;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    public Employee addEmployee(@Valid Employee employee) {
        employee.setPassword(DEFAULT_PWD);
        return employeeRepository.save(employee);
    }

    public Clerk adjustPosition(AdjustVo adjustVo) throws NotAllowAdjustException {
        String position = adjustVo.getOriginPosition();
        String newPosition = adjustVo.getNewPosition();
        if (!checkIfCanAdjust(adjustVo.getId(),position)) {
            throw new NotAllowAdjustException();
        }
        Clerk clerk = new Clerk();
        if (position.equalsIgnoreCase(EMPLOYEE_POSITION)) {
            clerk = employeeRepository.findById(adjustVo.getId()).orElse(null);
        } else if (position.equalsIgnoreCase(ADMIN_POSITION) || position.equalsIgnoreCase(MANAGE_POSITION)) {
            clerk = manageRepository.findById(adjustVo.getId()).orElse(null);
        } else {
            clerk = parkingBoyRepository.findById(adjustVo.getId()).orElse(null);
        }
        if (clerk != null) {
            clerk = saveToNewPosition(adjustVo.getNewPosition(), adjustVo.getOriginPosition(), clerk);
        }
        clerk.setPosition(newPosition);
        return clerk;
    }

    private Clerk saveToNewPosition(String newPosition, String originPosition, Clerk clerk) {
        if (newPosition.equalsIgnoreCase(EMPLOYEE_POSITION)) {
            Employee employee = clerkTOEmployee(clerk);
            deleteFromOriginPosition(clerk.getId(), originPosition);
            clerk = employeeRepository.save(employee);
        } else if (newPosition.equalsIgnoreCase(ADMIN_POSITION) || newPosition.equalsIgnoreCase(MANAGE_POSITION)) {
            Manage manage = clerkToAdmin(clerk);
            if (originPosition.equalsIgnoreCase(ADMIN_POSITION)) {
                manage.setStatus(0);
            } else if (originPosition.equalsIgnoreCase(MANAGE_POSITION)) {
                manage.setStatus(50);
            }else {
                deleteFromOriginPosition(clerk.getId(), originPosition);
            }
            clerk = manageRepository.save(manage);
        } else {
            ParkingBoy parkingBoy = clerkToParkingBoy(clerk);
            deleteFromOriginPosition(clerk.getId(), originPosition);
            clerk = parkingBoyRepository.save(parkingBoy);
        }
        return clerk;
    }

    private void deleteFromOriginPosition(int id, String originPosition) {
        if (originPosition.equalsIgnoreCase(EMPLOYEE_POSITION)) {
            employeeRepository.deleteById(id);
        } else if (originPosition.equalsIgnoreCase(ADMIN_POSITION) || originPosition.equalsIgnoreCase(MANAGE_POSITION)) {
            manageRepository.deleteById(id);
        } else {
            parkingBoyRepository.deleteById(id);
        }
    }

    private boolean checkIfCanAdjust(int boyId, String position) {
        if(!position.equalsIgnoreCase(BOY_POSITION)){
            return true;
        }
        ParkingBoy parkingBoy = parkingBoyRepository.findById(boyId).orElse(null);
        List<ParkingOrder> parkingOrders = parkingOrderRepository.findAllByParkingBoyAndStatusIsLessThan(parkingBoy, 6);
        return parkingOrders.size() == 0;
    }

    private Employee clerkTOEmployee(Clerk clerk) {
        Employee employee = new Employee();
        employee.setEmail(clerk.getEmail());
        employee.setName(clerk.getName());
        employee.setPassword(clerk.getPassword());
        employee.setPhoneNumber(clerk.getPhoneNumber());
        employee.setStatus(0);
        employee.setUsername(clerk.getUsername());
        employee.setPosition(EMPLOYEE_POSITION);
        return employee;
    }

    private Manage clerkToAdmin(Clerk clerk) {
        Manage manage = new Manage();
        manage.setEmail(clerk.getEmail());
        manage.setName(clerk.getName());
        manage.setPassword(clerk.getPassword());
        manage.setPhoneNumber(clerk.getPhoneNumber());
        manage.setStatus(0);
        manage.setUsername(clerk.getUsername());
        return manage;
    }

    private ParkingBoy clerkToParkingBoy(Clerk clerk) {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setEmail(clerk.getEmail());
        parkingBoy.setName(clerk.getName());
        parkingBoy.setPassword(clerk.getPassword());
        parkingBoy.setPhoneNumber(clerk.getPhoneNumber());
        parkingBoy.setStatus(0);
        parkingBoy.setUsername(clerk.getUsername());
        parkingBoy.setPosition(BOY_POSITION);
        return parkingBoy;
    }
}
