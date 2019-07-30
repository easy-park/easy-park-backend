package com.oocl.easyparkbackend.ParkingLot.Service;

import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotNameAndCapacityNotNull;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotRangeErrorException;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import com.oocl.easyparkbackend.ParkingOrder.Exception.ParkingOrderIdErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private UserOperator userOperator;
    @Autowired
    private ParkingLotRepository parkingLotRepository;


    public List<ParkingLot> getParkingLotByParkingBoy() {
        User user = userOperator.getUser();
        Integer parkingBoyId = user.getId();
        if (parkingBoyId == null) {
            throw new ParkingBoyIdErrorException();
        }
        Optional<ParkingBoy> optionalParkingBoy = parkingBoyRepository.findById(parkingBoyId);
        if (optionalParkingBoy.isPresent()) {
            return optionalParkingBoy.get().getParkingLotList();
        }
        throw new ParkingBoyIdErrorException();
    }

    public List<ParkingLot> getAllParkingLot() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.addAll(parkingLotRepository.findAll());
        return parkingLots;
    }

    public List<ParkingLot> findParkingLotsByName(String name) {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.addAll(parkingLotRepository.findByNameLike("%" + name + "%"));
        return parkingLots;
    }

    public ParkingLot update(ParkingLot parkingLot) {
        if (parkingLot.getName() == null || parkingLot.getName().length() == 0 || parkingLot.getCapacity() == null) {
            throw new ParkingLotNameAndCapacityNotNull();
        }
        return parkingLotRepository.findById(parkingLot.getId())
                .map(dbParkingLot -> {
                    dbParkingLot.setName(parkingLot.getName());
                    dbParkingLot.setCapacity(parkingLot.getCapacity());
                    dbParkingLot.setAvailable(parkingLot.getCapacity());
                    dbParkingLot.setStatus(parkingLot.getStatus());
                    return parkingLotRepository.save(dbParkingLot);
                }).orElseThrow(ParkingOrderIdErrorException::new);
    }

    public List<ParkingLot> getParkingLotsByRange(int start, int end) {
        if (start < 0 || end == 0) {
            throw new ParkingLotRangeErrorException();
        }
        if (start > end) {
            return parkingLotRepository.findByCapacityBetween(end, start);
        }
        return parkingLotRepository.findByCapacityBetween(start, end);
    }

    public List<ParkingLot> updateParkingLotStatus(ParkingLot parkingLot) {

        return null;
    }
}
