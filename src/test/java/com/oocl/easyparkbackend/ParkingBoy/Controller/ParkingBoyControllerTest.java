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

import static org.mockito.ArgumentMatchers.*;
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

        when(parkingBoyService.login(any())).thenReturn(anyString());
        ResultActions resultActions = mvc.perform(post("/parkingBoys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(parkingBoy)));

        resultActions.andExpect(status().isOk());
        verify(parkingBoyService).login(any());

    }

    @Test
    public void should_return_parkingBoy_when_invoke_login_given_userName_and_password() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setUsername("Sean");
        parkingBoy.setPassword("123");

        when(parkingBoyService.login(any())).thenReturn(anyString());
        ResultActions resultActions = mvc.perform(post("/parkingBoys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(parkingBoy)));

        resultActions.andExpect(status().isOk());
        verify(parkingBoyService).login(any());
    }

    @Test
    public void should_return_parkingBoy_when_invoke_login_given_phoneNumber_and_password() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setPhoneNumber("15574957517");
        parkingBoy.setPassword("123");

        when(parkingBoyService.login(any())).thenReturn(anyString());
        ResultActions resultActions = mvc.perform(post("/parkingBoys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(parkingBoy)));

        resultActions.andExpect(status().isOk());
        verify(parkingBoyService).login(any());
    }

    @Test
    public void should_return_parkingBoy_when_invoke_find_parkingBoy() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setId(123);
        parkingBoy.setPhoneNumber("15574957517");
        parkingBoy.setPassword("123");

        when(parkingBoyService.findParkingBoy()).thenReturn(parkingBoy);

        ResultActions resultActions = mvc.perform(get("/parkingBoys"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void should_return_parkingBoys_when_invoke_getAllParkingBoy() throws Exception {
        List<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy1 = new ParkingBoy();
        parkingBoy1.setName("sean");
        ParkingBoy parkingBoy2 = new ParkingBoy();
        parkingBoy2.setName("sean2");
        parkingBoys.add(parkingBoy1);
        parkingBoys.add(parkingBoy2);

        when(parkingBoyService.getAllParkingBoy()).thenReturn(parkingBoys);
        ResultActions resultActions = mvc.perform(get("/parkingBoys/all"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].name").value("sean"))
                .andExpect(jsonPath("$.data.[1].name").value("sean2"));
    }

    @Test
    public void should_return_parkingBoys_when_invoke_findParkingBoysByName_given_name() throws Exception {
        List<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy1 = new ParkingBoy();
        parkingBoy1.setName("sean");
        ParkingBoy parkingBoy2 = new ParkingBoy();
        parkingBoy2.setName("sean2");
        parkingBoys.add(parkingBoy1);
        parkingBoys.add(parkingBoy2);

        when(parkingBoyService.findParkingBoysByName(anyString())).thenReturn(parkingBoys);
        ResultActions resultActions = mvc.perform(get("/parkingBoys/list").param("name","sean"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].name").value("sean"));
    }

    @Test
    public void should_return_parkingBoys_when_invoke_findParkingBoysByPhoneNumber_given_phoneNumber() throws Exception {
        List<ParkingBoy> parkingBoys = new ArrayList<>();
        ParkingBoy parkingBoy1 = new ParkingBoy();
        parkingBoy1.setName("sean");
        parkingBoy1.setPhoneNumber("15574957517");
        ParkingBoy parkingBoy2 = new ParkingBoy();
        parkingBoy2.setName("sean2");
        parkingBoy2.setPhoneNumber("13055192867");
        parkingBoys.add(parkingBoy1);
        parkingBoys.add(parkingBoy2);

        when(parkingBoyService.findParkingBoysByPhoneNumber(anyString())).thenReturn(parkingBoys);
        ResultActions resultActions = mvc.perform(get("/parkingBoys/list").param("phoneNumber","155"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].phoneNumber").value("15574957517"));
    }

    @Test
    public void should_return_parkingBoy_when_invoke_findParkingBoyById() throws Exception {
        ParkingBoy parkingBoy1 = new ParkingBoy();
        parkingBoy1.setId(1);
        parkingBoy1.setName("sean");
        parkingBoy1.setPhoneNumber("15574957517");

        when(parkingBoyService.findParkingBoyById(anyInt())).thenReturn(parkingBoy1);
        ResultActions resultActions = mvc.perform(get("/parkingBoys/one").param("parkingBoyId","1"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("sean"));
    }
}
