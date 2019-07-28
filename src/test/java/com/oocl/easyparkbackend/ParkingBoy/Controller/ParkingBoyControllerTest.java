package com.oocl.easyparkbackend.ParkingBoy.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingBoyController.class)
public class ParkingBoyControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ParkingBoyService parkingBoyService;

    @Test
    public void should_return_parkingBoy_when_invoke_login_given_email_and_password() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setEmail("1052817017@qq.com");
        parkingBoy.setPassword("123");

        when(parkingBoyService.login(any())).thenReturn(parkingBoy);
        ResultActions resultActions = mvc.perform(post("/parkingBoys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(parkingBoy)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(parkingBoy.getEmail()))
                .andExpect(jsonPath("$.data.password").value(parkingBoy.getPassword()));
        verify(parkingBoyService).login(any());

    }

    @Test
    public void should_return_parkingBoy_when_invoke_login_given_userName_and_password() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setUsername("Sean");
        parkingBoy.setPassword("123");

        when(parkingBoyService.login(any())).thenReturn(parkingBoy);
        ResultActions resultActions = mvc.perform(post("/parkingBoys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(parkingBoy)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value(parkingBoy.getUsername()));
        verify(parkingBoyService).login(any());
    }

    @Test
    public void should_return_parkingBoy_when_invoke_login_given_phoneNumber_and_password() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setPhoneNumber("15574957517");
        parkingBoy.setPassword("123");

        when(parkingBoyService.login(any())).thenReturn(parkingBoy);
        ResultActions resultActions = mvc.perform(post("/parkingBoys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(parkingBoy)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.phoneNumber").value(parkingBoy.getPhoneNumber()));
        verify(parkingBoyService).login(any());
    }

    @Test
    void should_return_parkingLots_when_invoke_getParkingLotsByParkingBoy() throws Exception {
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setId("1");
        parkingLot1.setName("停车场1");
        parkingLot1.setAvailable(20);
        parkingLot1.setCapacity(10);
        parkingLotList.add(parkingLot1);

        when(parkingBoyService.getParkingLots(anyString())).thenReturn(parkingLotList);
        ResultActions resultActions = mvc.perform(get("/parkingBoys"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("停车场1"));

    }
}
