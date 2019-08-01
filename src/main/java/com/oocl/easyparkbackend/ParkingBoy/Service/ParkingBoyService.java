package com.oocl.easyparkbackend.ParkingBoy.Service;

import com.itmuch.lightsecurity.jwt.JwtOperator;
import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.FrozenParkingBoyException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.NotFindParkingBoyException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingBoyService {

    @Autowired
    private ParkingBoyRepository repository;
    @Autowired
    private JwtOperator jwtOperator;
    @Autowired
    private UserOperator userOperator;

    public String login(ParkingBoy parkingBoy) {
        Optional<ParkingBoy> optionalParkingBoy = Optional.empty();
        if (parkingBoy.getEmail() != null && parkingBoy.getPassword() != null) {
            optionalParkingBoy = repository.getByEmailAndPassword(parkingBoy.getEmail(), parkingBoy.getPassword());
        }
        if (parkingBoy.getUsername() != null && parkingBoy.getPassword() != null) {
            optionalParkingBoy = repository.getByUsernameAndPassword(parkingBoy.getUsername(), parkingBoy.getPassword());
        }
        if (parkingBoy.getPhoneNumber() != null && parkingBoy.getPassword() != null) {
            optionalParkingBoy = repository.getByPhoneNumberAndPassword(parkingBoy.getPhoneNumber(), parkingBoy.getPassword());
        }
        if (optionalParkingBoy.isPresent()) {
            if (optionalParkingBoy.get().getStatus() == 5) {
                throw new FrozenParkingBoyException();
            }
            User user = User.builder()
                    .id(Integer.valueOf(optionalParkingBoy.get().getId()))
                    .username(optionalParkingBoy.get().getUsername())
                    .roles(Arrays.asList("parkingBoy"))
                    .build();
            return jwtOperator.generateToken(user);
        }
        throw new UserNameOrPasswordErrorException();
    }

    public ParkingBoy findParkingBoy() {
        User user = userOperator.getUser();
        ParkingBoy parkingBoy = repository.findById(user.getId()).orElse(null);
        if (parkingBoy == null) {
            throw new NotFindParkingBoyException();
        }
        return parkingBoy;
    }

    public List<ParkingBoy> getAllParkingBoy() {
        List<ParkingBoy> returnParkingBoys = new ArrayList<>();
        returnParkingBoys.addAll(repository.findAll());
        return returnParkingBoys;
    }

    public List<ParkingBoy> findParkingBoysByName(String name) {
        List<ParkingBoy> returnParkingBoys = new ArrayList<>();
        returnParkingBoys.addAll(repository.findByNameLike("%" + name + "%"));
        return returnParkingBoys;
    }

    public List<ParkingBoy> findParkingBoysByPhoneNumber(String phoneNumber) {
        List<ParkingBoy> returnParkingBoys = new ArrayList<>();
        returnParkingBoys.addAll(repository.findByPhoneNumberLike("%" + phoneNumber + "%"));
        return returnParkingBoys;
    }

    public List<ParkingLot> findParkingLotList(Integer parkingBoyId) {
        if (parkingBoyId != null && parkingBoyId != 0) {
            ParkingBoy parkingBoyFind = repository.findById(parkingBoyId).orElse(null);
            if (parkingBoyFind != null) {
                return parkingBoyFind.getParkingLotList();
            }
        }
        throw new ParkingBoyIdErrorException();
    }

    public ParkingBoy setParkingBoysParkingLot(List<ParkingLot> lots, Integer parkingBoyId) {
        ParkingBoy parkingBoy = repository.findById(parkingBoyId).orElse(null);
        if (parkingBoy != null) {
            parkingBoy.setParkingLotList(lots);
            ParkingBoy parkingBoySave = repository.save(parkingBoy);
            return parkingBoySave;
        }
        throw new ParkingBoyIdErrorException();
    }

    public ParkingBoy findParkingBoyById(int id) {
        Optional<ParkingBoy> optionalParkingBoy = repository.findById(id);
        if (!optionalParkingBoy.isPresent()) {
            throw new NotFindParkingBoyException();
        }
        return optionalParkingBoy.get();
    }
}
