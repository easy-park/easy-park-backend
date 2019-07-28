package com.oocl.easyparkbackend.ParkingOrder.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Service.ParkingOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ParkingOrderControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private ParkingOrderService parkingOrderService;

    protected MockMvc mockMvc;

    @BeforeEach
    void setup(){ mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); }

    @Test
    public void should_return_historical_order_when_get_to_parking_order_given_status_is_finish() throws Exception {
        List<ParkingOrder> parkingOrderList = new ArrayList<>();
        ParkingOrder parkingOrder =  new ParkingOrder("123","eree",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),5.0,6,new ParkingBoy(),new ParkingLot());
        parkingOrderList.add(parkingOrder);
        when(parkingOrderService.findParkingOrderByStatus(anyInt(),anyInt())).thenReturn(parkingOrderList);

        ResultActions result = mockMvc.perform(get("/parkingOrders").param("status", "6"));

        result.andExpect(status().isOk());
    }

    @Test
    public void should_return_parkingOrder_when_put_to_parking_orders_given_status() throws Exception {
        ParkingOrder parkingOrder =  new ParkingOrder("123","eree",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),5.0,6,new ParkingBoy(),new ParkingLot());
        when(parkingOrderService.updateParkingOrderStatus(anyString(),anyInt())).thenReturn(parkingOrder);

        ResultActions result = mockMvc.perform(put("/parkingOrders/{orderId}","sdfasf").param("status","2"));

        result.andExpect(status().isOk());

    }


    @Test
    public void should_return_parkingBoy_unfinished_orders_and_will_fetch_first_given_parkingBoy_id() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy(1234567,"username","199729","stefan","13192269125",1,"953181215@qq.com",null);
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy, null);
        List<ParkingOrder> orders = new ArrayList<>();
        orders.add(order);

        when(parkingOrderService.findParkingBoyUnfinishedOrders(anyInt())).thenReturn(orders);

        ResultActions resultActions = mockMvc.perform(get("/parkingOrders")
                .param("parkingBoyId", "1234567"));

        resultActions.andExpect(status().isOk());
        verify(parkingOrderService).findParkingBoyUnfinishedOrders(any());
    }


}
