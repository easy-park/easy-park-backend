package com.oocl.easyparkbackend.ParkingBoy.Integration;


import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ParkingBoyIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;


    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ParkingBoyService parkingBoyService;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_return_parkingBoy_list_invoke_getAllParkingBoy() throws Exception {
        parkingBoyRepository.deleteAll();
        ParkingBoy parkingBoy = new ParkingBoy("username1", "username1", "username1", "1313215", 1, "123", new ArrayList<ParkingLot>());
        ParkingBoy parkingBoy1 = new ParkingBoy("username2", "123", "123", "1313215452", 1, "123", new ArrayList<ParkingLot>());

        parkingBoyRepository.save(parkingBoy);
        parkingBoyRepository.save(parkingBoy1);
        List<ParkingBoy> parkingBoys = parkingBoyRepository.findAll();

        ResultActions resultActions = mockMvc.perform(get("/parkingBoys/all"));

        resultActions.andExpect(jsonPath("$.data.[0].username", is("username1")))
                .andExpect(jsonPath("$.data.[1].username", is("username2")));


        assertEquals(parkingBoys.size(), 2);
        parkingBoyRepository.deleteAll();

    }

    @Test
    public void should_return_parkingBoy_list_invoke_findParkingBoysByName() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("billy", "username1", "billy", "1313215", 1, "123", new ArrayList<ParkingLot>());
        ParkingBoy parkingBoy1 = new ParkingBoy("xiaoming", "123", "xiaoming", "1313215452", 1, "123", new ArrayList<ParkingLot>());

        parkingBoyRepository.save(parkingBoy);
        parkingBoyRepository.save(parkingBoy1);

        ResultActions resultActions = mockMvc.perform(get("/parkingBoys/list").param("name","xiao"));

        resultActions.andExpect(jsonPath("$.data.[0].username", is("xiaoming")));

        List<ParkingBoy> parkingBoys = parkingBoyRepository.findByNameLike("%ming%");

        assertEquals(1,parkingBoys.size());
        parkingBoyRepository.deleteAll();
    }

}