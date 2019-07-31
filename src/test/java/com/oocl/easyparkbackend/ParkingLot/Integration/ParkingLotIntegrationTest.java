package com.oocl.easyparkbackend.ParkingLot.Integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingLotIntegrationTest {
    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_return_parkingLots_when_invoke_findByNameLike_given_name() throws Exception {
        ParkingLot parkingLot = new ParkingLot("123", "parkinglot1", 20, 10);
        parkingLotRepository.save(parkingLot);

        ResultActions resultActions = mockMvc.perform(get("/parking_lots").param("name", "lot1"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].name").value("parkinglot1"));
        assertThat(parkingLotRepository.findByNameLike("%lot1%").get(0).getName().equals("parkinglot1"));
    }

    @Test
    public void should_return_parkingLots_when_invoke_findAllParkingLot() throws Exception {
        ParkingLot parkingLot = new ParkingLot("123", "parkinglot1", 20, 10);
        parkingLotRepository.save(parkingLot);

        ResultActions resultActions = mockMvc.perform(get("/parking_lots"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].name").value("parkinglot1"));
        assertThat(parkingLotRepository.findAll().get(0).getName().equals("parkinglot1"));
    }

    @Test
    public void should_return_parkingLots_when_invoke_getParkingLotsByRange() throws Exception {
        ParkingLot parkingLot = new ParkingLot("parkinglot1", 20, 10);
        parkingLotRepository.save(parkingLot);

        ResultActions resultActions = mockMvc.perform(get("/parking_lots?start=1&end=30"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].name").value("parkinglot1"));
        assertThat(parkingLotRepository.findByCapacityBetween(1, 30).get(0).getName().equals("parkinglot1"));
    }

    @Test
    public void should_return_parkingLot_when_invoke_updateParkingLot() throws Exception {
        ParkingLot parkingLot = new ParkingLot("parkinglot1", 20, 10);
        ParkingLot lot = parkingLotRepository.save(parkingLot);
        ParkingLot parkingLotChanage = lot;
        parkingLotChanage.setName("parkinglot2");

        ResultActions resultActions = mockMvc.perform(put("/parking_lots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(parkingLotChanage)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("parkinglot2"));
        assertThat(parkingLotRepository.findById(parkingLot.getId()).get().getName().equals("parkinglot2"));
    }

    @Test
    public void should_save_the_parkingLot_message_when_post_to_parking_lots() throws Exception {
        String jsonString="        {\n" +
                "            \"name\": \"kkddd\",\n" +
                "            \"capacity\": 50,\n" +
                "            \"available\": 50\n" +
                "        }";

        ResultActions result = mockMvc.perform(post("/parking_lots").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.name").value("kkddd"));
        assertThat(parkingLotRepository.findByNameLike("kkddd").get(0).getCapacity()).isEqualTo(50);
        parkingLotRepository.deleteAll();


    }
}
