package com.oocl.easyparkbackend.Clerk.Service;

import com.oocl.easyparkbackend.Clerk.Entity.Clerk;
import com.oocl.easyparkbackend.Employee.Entity.Employee;
import com.oocl.easyparkbackend.Employee.Repository.EmployeeRepository;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Repository.ManageRepository;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClerkService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ManageRepository manageRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Clerk> showAllClerkMessage() {
        List<Clerk> clerkList = new ArrayList<>();
        clerkList.addAll(manageRepository.findAll());
        clerkList.addAll(parkingBoyRepository.findAll());
        clerkList.addAll(employeeRepository.findAll());
        List<Clerk> returnList = new ArrayList<>();
        returnList.addAll(updatePosition(clerkList));
        return returnList;
    }

    private List<Clerk> updatePosition(List<Clerk> clerkList) {
        return clerkList.stream().map(x -> {
                    if (x instanceof ParkingBoy) {
                        x.setPosition("ParkingBoy");
                    } else if (x instanceof Employee) {
                        x.setPosition("Employee");
                    } else if (x instanceof Manage) {
                        x.setPosition("Manage");
                    }
                    return x;
                }
        ).collect(Collectors.toList());
    }

    public List<Clerk> findClerkMessageByName(String name) {
        List<Clerk> clerkList = new ArrayList<>();
        clerkList.addAll(manageRepository.findAllByNameLike("%" + name + "%"));
        clerkList.addAll(parkingBoyRepository.findByNameLike("%" + name + "%"));
        clerkList.addAll(employeeRepository.findAll());
        List<Clerk> returnList = new ArrayList<>();
        returnList.addAll(updatePosition(clerkList));
        return clerkList;
    }

    public List<Clerk> findClerkMessageByPhone(String phone) {
        List<Clerk> clerkList = new ArrayList<>();
        clerkList.addAll(manageRepository.findAllByPhoneNumberLike("%" + phone + "%"));
        clerkList.addAll(parkingBoyRepository.findByPhoneNumberLike("%" + phone + "%"));
        List<Clerk> returnList = new ArrayList<>();
        returnList.addAll(updatePosition(clerkList));
        return clerkList;
    }

    public List<Clerk> findClerkMessageById(Integer id) {
        List<Clerk> clerkList = new ArrayList<>();
        clerkList.add(manageRepository.findById(id).orElse(null));
        clerkList.add(parkingBoyRepository.findById(id).orElse(null));
        List<Clerk> returnList = new ArrayList<>();
        returnList.addAll(updatePosition(clerkList));
        return clerkList;
    }

    public List<Clerk> findClerkMessageByEmail(String email) {
        List<Clerk> clerkList = new ArrayList<>();
        clerkList.addAll(manageRepository.findAllByEmailLike("%" + email + "%"));
        clerkList.addAll(parkingBoyRepository.findByEmailLike("%" + email + "%"));
        List<Clerk> returnList = new ArrayList<>();
        returnList.addAll(updatePosition(clerkList));
        return clerkList;
    }
}
