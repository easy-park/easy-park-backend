package com.oocl.easyparkbackend.Manage.Service;

import com.itmuch.lightsecurity.jwt.JwtOperator;
import com.itmuch.lightsecurity.jwt.User;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Repository.ManageRepository;
import com.oocl.easyparkbackend.Manage.Vo.BoysLotVo;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    public ParkingBoy setParkingBoysParkingLots(BoysLotVo vo) {
        List<ParkingLot> lots = new ArrayList<>();
        List<ParkingLot> lotList = parkingBoyService.findParkingLotList(vo.getId());
        for (String id : vo.getList()) {
            ParkingLot parkingLot = parkingLotService.findParkingLotsById(String.valueOf(id));
            if (parkingLot != null){
                parkingLot.setStatus(2);
                ParkingLot parkingLotSave = parkingLotService.save(parkingLot);
                lots.add(parkingLotSave);
            }
        }
        lotList.addAll(lots);
        return parkingBoyService.setParkingBoysParkingLot(lotList,vo.getId());
    }

    public ParkingBoy changeParkingBoysParkingLots(BoysLotVo vo) {
        List<ParkingLot> lotList = parkingBoyService.findParkingLotList(vo.getId());
        for (String id : vo.getList()) {
            ParkingLot parkingLot = parkingLotService.findParkingLotsById(String.valueOf(id));
            if (parkingLot != null){
                lotList = lotList.stream().filter(item-> !item.getId().equals(String.valueOf(id))).collect(Collectors.toList());
                parkingLot.setStatus(1);
                parkingLotService.save(parkingLot);
            }
        }
        return parkingBoyService.setParkingBoysParkingLot(lotList,vo.getId());
    }

    public String login(Manage manage) {
        Optional<Manage> optionalManager = Optional.empty();
        if (manage.getEmail() != null && manage.getPassword() != null) {
            optionalManager = manageRepository.getByEmailAndPassword(manage.getEmail(), manage.getPassword());
        }
        if (manage.getUsername() != null && manage.getPassword() != null) {
            optionalManager = manageRepository.getByUsernameAndPassword(manage.getUsername(), manage.getPassword());
        }
        if (manage.getPhoneNumber() != null && manage.getPassword() != null) {
            optionalManager = manageRepository.getByPhoneNumberAndPassword(manage.getPhoneNumber(), manage.getPassword());
        }
        if (optionalManager.isPresent()) {
            User user = User.builder()
                    .id(Integer.valueOf(optionalManager.get().getId()))
                    .username(optionalManager.get().getUsername())
                    .roles(Arrays.asList("manager"))
                    .build();
            return jwtOperator.generateToken(user);
        }
        throw new UserNameOrPasswordErrorException();
    }
}
