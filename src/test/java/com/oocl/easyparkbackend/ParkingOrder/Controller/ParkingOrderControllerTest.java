package com.oocl.easyparkbackend.ParkingOrder.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.ParkingBoy.Controller.ParkingBoyController;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Exception.LoginTokenExpiredException;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Exception.ParkingLotIdErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Exception.OrderNotExistException;
import com.oocl.easyparkbackend.ParkingOrder.Exception.ParkingOrderIdErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Service.ParkingOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingOrderController.class)
public class ParkingOrderControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private ParkingOrderService parkingOrderService;

    protected MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_return_historical_order_when_get_to_parking_order_given_status_is_finish() throws Exception {
        List<ParkingOrder> parkingOrderList = new ArrayList<>();
        ParkingOrder parkingOrder = new ParkingOrder("123", "eree", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), 5.0, 6, new ParkingBoy(), new ParkingLot());
        parkingOrderList.add(parkingOrder);
        when(parkingOrderService.findParkingOrderByStatus(anyInt())).thenReturn(parkingOrderList);

        ResultActions result = mockMvc.perform(get("/parkingOrders").param("status", "6"));

        result.andExpect(status().isOk());
    }

    @Test
    public void throw_exception_when_get_to_parking_order_given_status_is_finish() throws Exception{
        when(parkingOrderService.findParkingOrderByStatus(anyInt())).thenThrow(new LoginTokenExpiredException());

        try {
            ResultActions result = mockMvc.perform(get("/parkingOrders").param("status", "6"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.msg").value("登录过期，请重新登陆！"))
                    .andDo(print());
        } catch (LoginTokenExpiredException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_return_parkingOrder_when_put_to_parking_orders_given_status() throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder("123", "eree", new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()), 5.0, 6, new ParkingBoy(), new ParkingLot());
        when(parkingOrderService.updateParkingOrderStatus(anyString(), anyInt())).thenReturn(parkingOrder);

        ResultActions result = mockMvc.perform(put("/parkingOrders/{orderId}", "sdfasf").param("status", "2"));

        result.andExpect(status().isOk());

    }


    @Test
    public void should_return_parkingBoy_unfinished_orders_and_will_fetch_first_given_parkingBoy_id() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", null);
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy, null);
        List<ParkingOrder> orders = new ArrayList<>();
        orders.add(order);

        when(parkingOrderService.findParkingBoyUnfinishedOrders()).thenReturn(orders);

        ResultActions resultActions = mockMvc.perform(get("/parkingOrders"));

        resultActions.andExpect(status().isOk());
        verify(parkingOrderService).findParkingBoyUnfinishedOrders();
    }

    @Test
    public void should_return_parkingOrder_when_invoke_finishRobOrder_given_parkingOrderId_and_parkingLotId() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", null);
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy, null);
        when(parkingOrderService.finishRobOrder(anyString(), anyString())).thenReturn(order);

        ResultActions resultActions = mockMvc.perform(get("/parkingOrders").param("parkingOrderId", "123456").param("parkingLotId", "123456"));

        resultActions.andExpect(status().isOk());

    }

    @Test
    public void throw_exception_invoke_finishRobOrder_given_parkingOrderId_and_parkingLotId() throws Exception {
        when(parkingOrderService.finishRobOrder(anyString(), anyString())).thenThrow(new ParkingLotIdErrorException());

        try {
            ResultActions result = mockMvc.perform(get("/parkingOrders").param("parkingOrderId", "123456").param("parkingLotId", "123456"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.msg").value("ParkingLotId Error"))
                    .andDo(print());
        } catch (ParkingLotIdErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_return_parkingOrder_when_invoke_getParkingOrder_given_parkingOrderId() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", null);
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy, null);

        when(parkingOrderService.getOrderById(anyString())).thenReturn(order);
        ResultActions resultActions = mockMvc.perform(get("/parkingOrders/1"));

        resultActions.andExpect(status().isOk());
        verify(parkingOrderService).getOrderById(anyString());
    }

    @Test
    public void should_throw_exception_when_invoke_getParkingOrder_given_parkingOrderId() throws Exception {
        when(parkingOrderService.getOrderById(anyString())).thenThrow(new ParkingOrderIdErrorException());

        try {
        ResultActions resultActions = mockMvc.perform(get("/parkingOrders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("ParkingOrderId Error"))
                .andDo(print());
        } catch (ParkingLotIdErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_return_parkingOrder_when_invoke_receiveOrder_given_parkingOrderId() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", null);
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy, null);
        when(parkingOrderService.receiveOrder(anyString())).thenReturn(order);

        ResultActions resultActions = mockMvc.perform(put("/parkingOrders").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(order)));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void should_throw_exception_when_invoke_receiveOrder_given_parkingOrderId() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", null);
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy, null);
        when(parkingOrderService.receiveOrder(anyString())).thenThrow(new OrderNotExistException());

        try{
        ResultActions resultActions = mockMvc.perform(put("/parkingOrders").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Order not exist"))
                .andDo(print());
        } catch (OrderNotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_return_parkingOrder_when_invoke_generateParkingOrder_given_carNumber() throws Exception {
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 1, null, null);
        when(parkingOrderService.generateParkingOrder(anyString())).thenReturn(order);

        ResultActions resultActions = mockMvc.perform(post("/parkingOrders").param("carNumber", "123"));

        resultActions.andExpect(status().isOk());

    }

    @Test
    public void should_return_parkingOrder_when_invoke_getNotFinishParkingOrderByUser() throws Exception {
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 1, null, null);
        List<ParkingOrder> lots = new ArrayList<>();
        lots.add(order);
        when(parkingOrderService.getNotFinishParkingOrderByUser()).thenReturn(lots);

        ResultActions resultActions = mockMvc.perform(get("/parking_orders_customer"));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.[0].carNumber").value("55555"));
    }


    @Test
    public void should_return_all_parking_order_when_get_to_parking_order() throws Exception {
        List<ParkingOrder> parkingOrderList = new ArrayList<>();
        when(parkingOrderService.getAllParkingOrder()).thenReturn(parkingOrderList);

        ResultActions result = mockMvc.perform(get("/parkingorderlist"));

        result.andExpect(status().isOk());
    }

    @Test
    public void should_return_parking_order_list_when_invoke_searchParkingOrdersByCarNumber() throws Exception {
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 1, null, null);
        List<ParkingOrder> parkingOrders = new ArrayList<>();
        parkingOrders.add(order);
        when(parkingOrderService.searchParkingOrdersByCarNumber(anyString())).thenReturn(parkingOrders);

        ResultActions result = mockMvc.perform(get("/parkingorderlist?carnumber=5"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].carNumber").value("55555"));
    }

    @Test
    public void should_return_parking_order_list_when_invoke_getParkingOrderByType() throws Exception {
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 1, null, null);
        List<ParkingOrder> parkingOrders = new ArrayList<>();
        parkingOrders.add(order);
        when(parkingOrderService.getParkingOrderByType(anyString())).thenReturn(parkingOrders);

        ResultActions result = mockMvc.perform(get("/parkingorderlist?type=取车"));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[0].carNumber").value("55555"));

    }

    @Test
    public void should_return_parking_order_list_when_invoke_getParkingOrderByStatus() throws Exception {
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 1, null, null);
        List<ParkingOrder> parkingOrders = new ArrayList<>();
        parkingOrders.add(order);
        when(parkingOrderService.getParkingOrderByStatus(anyString())).thenReturn(parkingOrders);

        ResultActions resultActions = mockMvc.perform(get("/parkingorderlist?status=无人处理"));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.data.[0].carNumber").value("55555"));
    }

    @Test
    public void should_return_new_parking_order_list_when_invoke_assignParkingBoy() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", null);
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, parkingBoy, null);
        when(parkingOrderService.assignParkingBoy(anyString(), anyInt())).thenReturn(order);

        ResultActions resultActions = mockMvc.perform(get("/parkingorderlist?parkingOrderId=1&parkingBoyId=1"));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.data.carNumber").value("55555"));

    }

    @Test
    public void should_return_parkingOrder_list_when_invoke_getUnrepeatOrders() throws Exception {
        ParkingOrder order = new ParkingOrder("1", "55555", new Timestamp(System.currentTimeMillis()), null, null, 3, null, null);List<ParkingOrder> parkingOrders = new ArrayList<>();
        List<String> historyCarNumber = new ArrayList<>();
        historyCarNumber.add("55555");

        when(parkingOrderService.getUnrepeatOrders()).thenReturn(historyCarNumber);
        ResultActions resultActions = mockMvc.perform(get("/unrepeatcarnumbers"));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.data.[0]").value("55555"));
    }



}