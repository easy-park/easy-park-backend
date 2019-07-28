package com.oocl.easyparkbackend.ParkingOrder.Integration;


import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Repository.ParkingOrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ParkingOrderIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @BeforeEach
    void setup(){ mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); }

    @Test
    public void should_return_parking_order_and_update_parking_order_when_put_to_parking_order_given_order_id_and_status() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("12345","123","123","123","sdfsf",1,"12345",new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        ParkingLot parkingLot = new ParkingLot("224","456",5,5);
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("324","eree",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),5.0,1,parkingBoy,parkingLot);
        parkingOrderRepository.save(parkingOrder);

        ResultActions result = mockMvc.perform(put("/parkingOrders/{orderId}","324").param("status","2"));


        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.status",is(2)));
        assertThat(parkingOrderRepository.findById("324").get().getStatus().equals(2));
    }

    @Test
    public void should_update_parking_order_and_parking_boy_status_when_update_parking_order_status() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("12345","123","123","123","sdfsf",1,"12345",new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        ParkingLot parkingLot = new ParkingLot("224","456",5,5);
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("324","eree",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),5.0,2,parkingBoy,parkingLot);
        parkingOrderRepository.save(parkingOrder);

        ResultActions result = mockMvc.perform(put("/parkingOrders/{orderId}","324").param("status","3"));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.status",is(3)));
        assertThat(parkingOrderRepository.findById("324").get().getStatus().equals(3));
        assertThat(parkingBoyRepository.findById("12345").get().getStatus().equals(0));
    }
}
