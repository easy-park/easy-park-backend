package com.oocl.easyparkbackend.ParkingBoy.Service;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingBoyService {

    @Autowired
    private ParkingBoyRepository repository;

    public ParkingBoy login(ParkingBoy parkingBoy) {
        Optional<ParkingBoy> optionalParkingBoy = repository.getByEmailAndPassword(parkingBoy.getEmail(), parkingBoy.getPassword());
        if (optionalParkingBoy.isPresent()) {
            return optionalParkingBoy.get();
        }
        throw new UserNameOrPasswordErrorException();
    }
}
