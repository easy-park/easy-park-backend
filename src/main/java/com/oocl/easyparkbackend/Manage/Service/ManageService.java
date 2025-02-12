package com.oocl.easyparkbackend.Manage.Service;

import com.itmuch.lightsecurity.jwt.JwtOperator;
import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Exception.FrozenManagerException;
import com.oocl.easyparkbackend.Manage.Exception.NotFindManagerException;
import com.oocl.easyparkbackend.Manage.Repository.ManageRepository;
import com.oocl.easyparkbackend.Manage.Vo.BoysLotVo;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Service.ParkingLotService;
import com.oocl.easyparkbackend.exception.authentication.AuthenticateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManageService {
    @Autowired
    private ParkingBoyService parkingBoyService;
    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private ManageRepository manageRepository;
    @Autowired
    private JwtOperator jwtOperator;
    @Autowired
    private UserOperator userOperator;

    public ParkingBoy setParkingBoysParkingLots(BoysLotVo vo) {
        List<ParkingLot> lots = new ArrayList<>();
        List<ParkingLot> lotList = parkingBoyService.findParkingLotList(vo.getId());
        for (String id : vo.getList()) {
            ParkingLot parkingLot = parkingLotService.findParkingLotsById(String.valueOf(id));
            if (parkingLot != null) {
                parkingLot.setStatus(2);
                ParkingLot parkingLotSave = parkingLotService.save(parkingLot);
                lots.add(parkingLotSave);
            }
        }
        lotList.addAll(lots);
        return parkingBoyService.setParkingBoysParkingLot(lotList, vo.getId());
    }

    public ParkingBoy changeParkingBoysParkingLots(BoysLotVo vo) {
        List<ParkingLot> lotList = parkingBoyService.findParkingLotList(vo.getId());
        for (String id : vo.getList()) {
            ParkingLot parkingLot = parkingLotService.findParkingLotsById(String.valueOf(id));
            if (parkingLot != null) {
                lotList = lotList.stream().filter(item -> !item.getId().equals(String.valueOf(id))).collect(Collectors.toList());
                parkingLot.setStatus(1);
                parkingLotService.save(parkingLot);
            }
        }
        return parkingBoyService.setParkingBoysParkingLot(lotList, vo.getId());
    }

    public String login(Manage manage) {
        Optional<Manage> optionalManage;
        if (manage.getEmail() != null) {
            optionalManage = manageRepository.getByEmailAndPassword(manage.getEmail(), manage.getPassword());
        } else if (manage.getUsername() != null) {
            optionalManage = manageRepository.getByUsernameAndPassword(manage.getUsername(), manage.getPassword());
        } else if (manage.getPhoneNumber() != null) {
            optionalManage = manageRepository.getByPhoneNumberAndPassword(manage.getPhoneNumber(), manage.getPassword());
        } else {
            optionalManage = Optional.empty();
        }
        if (optionalManage.isPresent()) {
            if (optionalManage.get().getStatus() == 5) {
                throw new FrozenManagerException();
            }
        }
        return optionalManage.map(dbManage -> {
            User user = User.builder()
                    .id(dbManage.getId())
                    .username(dbManage.getUsername())
                    .roles(manage.roles())
                    .build();
            return jwtOperator.generateToken(user);
        }).orElseThrow(AuthenticateException::new);
    }

    public Manage getManager() {
        User user = userOperator.getUser();
        return manageRepository.findById(user.getId()).orElseThrow(NotFindManagerException::new);
    }
}
