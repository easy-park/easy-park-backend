package com.oocl.easyparkbackend.Clerk.Service;

import com.oocl.easyparkbackend.Clerk.Entity.Clerk;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Repository.ManageRepository;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClerkService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ManageRepository manageRepository;

    public List<Clerk> showAllClerkMessage() {
        List<Clerk> clerkList = new ArrayList<>();
        List<ParkingBoy> parkingBoyList = parkingBoyRepository.findAll();
        List<Manage> manageList = manageRepository.findAll();
        clerkList.addAll(manegeToClerk(manageList));
        clerkList.addAll(parkingBoyToClerk(parkingBoyList));


        return clerkList;
    }

    private List<Clerk> manegeToClerk(List<Manage> manageList) {
        List<Clerk> clerkList = new ArrayList<>();
        for (int i = 0; i < manageList.size(); i++) {
            Clerk clerk = new Clerk();
            clerk.setEmail(manageList.get(i).getEmail());
            clerk.setId(manageList.get(i).getId());
            clerk.setName(manageList.get(i).getName());
            clerk.setPassword(manageList.get(i).getPassword());
            clerk.setPhoneNumber(manageList.get(i).getPhoneNumber());
            clerk.setStatus(manageList.get(i).getStatus());
            clerk.setUsername(manageList.get(i).getUsername());
            clerk.setPosition("Manage");
            clerkList.add(clerk);
        }
        return clerkList;
    }

    private List<Clerk> parkingBoyToClerk(List<ParkingBoy> parkingBoyList) {
        List<Clerk> clerkList = new ArrayList<>();
        for (int i = 0; i < parkingBoyList.size(); i++) {
            Clerk clerk = new Clerk();
            clerk.setEmail(parkingBoyList.get(i).getEmail());
            clerk.setId(parkingBoyList.get(i).getId());
            clerk.setName(parkingBoyList.get(i).getName());
            clerk.setPassword(parkingBoyList.get(i).getPassword());
            clerk.setPhoneNumber(parkingBoyList.get(i).getPhoneNumber());
            clerk.setStatus(parkingBoyList.get(i).getStatus());
            clerk.setUsername(parkingBoyList.get(i).getUsername());
            clerk.setPosition("ParkingBoy");
            clerkList.add(clerk);
        }
        return clerkList;
    }
}
