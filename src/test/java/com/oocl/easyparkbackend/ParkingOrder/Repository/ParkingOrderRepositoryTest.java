package com.oocl.easyparkbackend.ParkingOrder.Repository;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ParkingOrderRepositoryTest {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Test
    public void should_return_historical_list_when_invoke_findAllByStatus(){
        ParkingBoy parkingBoy = new ParkingBoy("123","123","123","123","sdfsf",1,"12345",new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        ParkingLot parkingLot = new ParkingLot("123","456",5,5);
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("123","eree",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),5.0,6,parkingBoy,parkingLot);

        parkingOrderRepository.save(parkingOrder);
        List<ParkingOrder> parkingOrderList = parkingOrderRepository.findAllByStatus(6);

        assertThat(parkingOrderList.get(0).getCarNumber().equals("eree"));

    }
}
