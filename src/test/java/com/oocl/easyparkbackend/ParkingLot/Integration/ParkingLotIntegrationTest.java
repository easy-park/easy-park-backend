package com.oocl.easyparkbackend.ParkingLot.Integration;

import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import com.oocl.easyparkbackend.ParkingLot.Repository.ParkingLotRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void setup(){ mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); }

    @Test
    public void should_return_parkingLots_when_invoke_findByNameLike_given_name() throws Exception {
        ParkingLot parkingLot = new ParkingLot("123","parkinglot1",20,10);
        parkingLotRepository.save(parkingLot);

        ResultActions resultActions = mockMvc.perform(get("/parking_lots").param("name", "lot1"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].name").value("parkinglot1"));
        assertThat(parkingLotRepository.findByNameLike("%lot1%").get(0).getName().equals("parkinglot1"));
    }
}
