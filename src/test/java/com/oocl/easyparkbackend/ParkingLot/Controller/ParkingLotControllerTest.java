package com.oocl.easyparkbackend.ParkingLot.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Service.ParkingLotService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
public class ParkingLotControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ParkingLotService parkingLotService;

    @Test
    void should_return_parkingLots_when_invoke_getParkingLotsByParkingBoy() throws Exception {
        List<ParkingLot> parkingLotList = new ArrayList<>();
        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setId("1");
        parkingLot1.setName("停车场1");
        parkingLot1.setAvailable(20);
        parkingLot1.setCapacity(10);
        parkingLotList.add(parkingLot1);

        when(parkingLotService.getParkingLotByParkingBoy()).thenReturn(parkingLotList);
        ResultActions resultActions = mvc.perform(get("/parkingLots"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("停车场1"));
    }

    @Test
    void should_return_parkingLots_when_invoke_getAllParkingLots() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot1 = new ParkingLot("123", "parkinglot1", 40, 20);
        ParkingLot parkingLot2 = new ParkingLot("456", "parkinglot2", 40, 30);
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);

        when(parkingLotService.getAllParkingLot()).thenReturn(parkingLots);
        ResultActions resultActions = mvc.perform(get("/parking_lots"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    void should_return_parkingLots_when_invoke_findParkingLotsByName() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot1 = new ParkingLot("123", "parkinglot1", 40, 20);
        ParkingLot parkingLot2 = new ParkingLot("456", "parkinglot2", 40, 30);
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);

        when(parkingLotService.findParkingLotsByName(anyString())).thenReturn(parkingLots);
        ResultActions resultActions = mvc.perform(get("/parking_lots").param("name", "lot1"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    void should_update_parkingLot_when_invoke_updateParkingLot_given_parkingLot() throws Exception {
        ParkingLot parkingLot = new ParkingLot("123", "parkingLot", 10, 10);

        when(parkingLotService.update(any())).thenReturn(parkingLot);

        ResultActions resultActions = mvc.perform(put("/parking_lots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(parkingLot)));

        resultActions.andExpect(status().isOk());
    }
}
