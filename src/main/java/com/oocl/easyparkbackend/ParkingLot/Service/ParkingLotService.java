package com.oocl.easyparkbackend.ParkingLot.Service;

import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.ParkingBoyIdErrorException;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLotDashboradVO;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotNameAndCapacityNotNull;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotRangeErrorException;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import com.oocl.easyparkbackend.ParkingLot.Vo.LotsAndBoysLotsVo;
import com.oocl.easyparkbackend.ParkingOrder.Exception.ParkingOrderIdErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {
    @Autowired
    private ParkingBoyService parkingBoyService;
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

    public LotsAndBoysLotsVo getAllParkingListAndParkingBoysParkingLot(Integer parkingBoyId) {
        LotsAndBoysLotsVo lotsAndBoysLotsVo = new LotsAndBoysLotsVo();
        List<ParkingLot> parkingLotsBoys = parkingBoyService.findParkingLotList(parkingBoyId);
        List<ParkingLot> allParkingLots = this.getAllParkingLot();

        lotsAndBoysLotsVo.setParkingLots(allParkingLots);
        lotsAndBoysLotsVo.setBoysParkingLots(parkingLotsBoys);
        return lotsAndBoysLotsVo;
    }

    public ParkingLot findParkingLotsById(String id) {
        return parkingLotRepository.findById(id).orElse(null);
    }

    public ParkingLot save(ParkingLot parkingLot) {
        ParkingLot parkingLotSave = parkingLotRepository.save(parkingLot);
        return parkingLotSave;
    }

    public List<ParkingLot> updateParkingLotStatus(ParkingLot parkingLot) {

        return null;
    }

    public ParkingLot addParkingLot(@Valid ParkingLot parkingLot) {
        parkingLot.setStatus(1);
        ParkingLot returnParkingLot = parkingLotRepository.save(parkingLot);
        return returnParkingLot;
    }


    public List<ParkingLotDashboradVO> getParkingLotDashboard() {
        List<ParkingLotDashboradVO> parkingLotDashboradVOList = new ArrayList<>();
        List<ParkingLot> parkingLotList = parkingLotRepository.findAll();
        List<ParkingBoy> parkingBoyList = parkingBoyRepository.findAll();
        for (ParkingBoy parkingBoy : parkingBoyList) {
            parkingLotDashboradVOList.addAll(getParkingLotDashboardByBoy(parkingBoy));
        }
        for (ParkingLot parkingLot : parkingLotList) {
            if (getParkingLotDashboardByLot(parkingLotDashboradVOList,parkingLot)){
                parkingLotDashboradVOList.add(new ParkingLotDashboradVO(parkingLot,null));
            }
        }
        return parkingLotDashboradVOList;
    }

    private boolean getParkingLotDashboardByLot(List<ParkingLotDashboradVO> parkingLotDashboradVOList, ParkingLot parkingLot) {
        for(ParkingLotDashboradVO parkingLotDashboradVO:parkingLotDashboradVOList){
            if(parkingLotDashboradVO.getId() == parkingLot.getId()){
                return false;
            }
        }
        return true;
    }

    private List<ParkingLotDashboradVO> getParkingLotDashboardByBoy(ParkingBoy parkingBoy) {
        List<ParkingLotDashboradVO> parkingLotDashboradVOList = new ArrayList<>();
        for (int i = 0; i < parkingBoy.getParkingLotList().size(); i++) {
            parkingLotDashboradVOList.add(new ParkingLotDashboradVO(parkingBoy.getParkingLotList().get(i), parkingBoy));
        }
        return parkingLotDashboradVOList;
    }
}
