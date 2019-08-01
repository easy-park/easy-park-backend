package com.oocl.easyparkbackend.Manager.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ManagerIntegrationTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_assign_parking_lot_when_post_to_parkingBoy_given_BoyLotsVo() throws Exception {
        ParkingLot parkingLot1 = new ParkingLot("123", "parkinglot1", 20, 10);
        ParkingLot parkingLot2 = new ParkingLot("124", "parkinglot2", 20, 10);
        ParkingLot parkingLot3 = new ParkingLot("125", "parkinglot3", 20, 10);
        ParkingLot parkingLot4 = new ParkingLot("126", "parkinglot4", 20, 10);
        ParkingLot returnParkingLot1 = parkingLotRepository.save(parkingLot1);
        ParkingLot returnParkingLot2 = parkingLotRepository.save(parkingLot2);
        ParkingLot returnParkingLot3 = parkingLotRepository.save(parkingLot3);
        ParkingLot returnParkingLot4 = parkingLotRepository.save(parkingLot4);

        ParkingBoy parkingBoy = new ParkingBoy( "username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", new ArrayList<>());
        ParkingBoy parkingBoy1 = parkingBoyRepository.save(parkingBoy);

        String jsonString = "{\n" +
                " \"list\": [\""+returnParkingLot1.getId()+"\",\""+returnParkingLot2.getId()+"\",\""+returnParkingLot3.getId()+"\",\""+returnParkingLot4.getId()+"\"],\n" +
                " \"id\": "+parkingBoy1.getId()+"\n" +
                "}";

        ResultActions result = mockMvc.perform(post("/manager/parkingBoy").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("stefan"))
                .andExpect(jsonPath("$.data.parkingLotList.[0].name").value("parkinglot1"));
    }

    @Test
    public void should_change_parking_lot_from_parkingBoy_when_post_to_parkingBoy_given_BoyLotsVo() throws Exception {
        ParkingLot parkingLot1 = new ParkingLot("123", "parkinglot1", 20, 10);
        ParkingLot parkingLot2 = new ParkingLot("124", "parkinglot2", 20, 10);
        ParkingLot parkingLot3 = new ParkingLot("125", "parkinglot3", 20, 10);
        ParkingLot parkingLot4 = new ParkingLot("126", "parkinglot4", 20, 10);
        ParkingLot returnParkingLot1 = parkingLotRepository.save(parkingLot1);
        ParkingLot returnParkingLot2 = parkingLotRepository.save(parkingLot2);
        ParkingLot returnParkingLot3 = parkingLotRepository.save(parkingLot3);
        ParkingLot returnParkingLot4 = parkingLotRepository.save(parkingLot4);
        List<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(returnParkingLot1);
        parkingLotList.add(returnParkingLot2);
        parkingLotList.add(returnParkingLot3);
        parkingLotList.add(returnParkingLot4);

        ParkingBoy parkingBoy = new ParkingBoy( "username", "199729", "stefan", "13192269125", 1, "953181215@qq.com",parkingLotList);
        ParkingBoy parkingBoy1 = parkingBoyRepository.save(parkingBoy);

        String jsonString = "{\n" +
                " \"list\": [\""+returnParkingLot1.getId()+"\",\""+returnParkingLot2.getId()+"\",\""+returnParkingLot3.getId()+"\",\""+returnParkingLot4.getId()+"\"],\n" +
                " \"id\": "+parkingBoy1.getId()+"\n" +
                "}";

        ResultActions result = mockMvc.perform(put("/manager/parkingBoy").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("stefan"))
                .andExpect(jsonPath("$.data.parkingLotList").isEmpty());
    }

}
