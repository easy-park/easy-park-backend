package com.oocl.easyparkbackend.Clerk.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.Employee.Entity.Clerk;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Repository.ManageRepository;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
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

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClerkIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Autowired
    private ManageRepository manageRepository;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void should_return_real_clerk_list_when_get_to_clerks() throws Exception {
        manageRepository.deleteAll();
        parkingBoyRepository.deleteAll();
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan", "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);

        ResultActions result = mockMvc.perform(get("/clerks"));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[1].username", is("username")));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[0].username", is("use8855me")));
        manageRepository.deleteAll();
        parkingBoyRepository.deleteAll();
    }


    @Test
    public void should_return_real_clerk_list_when_get_to_clerks_given_10boys_and_5Manages() throws Exception {
        for (int i = 0; i < 10; i++) {
            ParkingBoy parkingBoy = new ParkingBoy("username" + i, "199729" + i, "stefan" + i, "13192269125" + i, 1, "953181215@qq.com" + i, new ArrayList<>());
            parkingBoyRepository.save(parkingBoy);
        }
        for (int i = 0; i < 5; i++) {
            Manage manage = new Manage(null, "use8855me" + i, "199529" + i, "st77fan" + i, "13192545625" + i, 1, "953188555@qq.com+i");
            manageRepository.save(manage);
        }

        ResultActions result = mockMvc.perform(get("/clerks"));

        for (int i = 0; i < 5; i++) {
            result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[" + i + "].username", is("use8855me" + i))).andExpect(jsonPath("$.data.[" + i + "].position", is("Manage")));
        }
        for (int i = 5; i < 15; i++) {
            result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[" + i + "].username", is("username" + (i - 5)))).andExpect(jsonPath("$.data.[" + i + "].position", is("ParkingBoy")));
        }
        manageRepository.deleteAll();
        parkingBoyRepository.deleteAll();
    }


    @Test
    public void should_return_clerk_list_message_when_get_to_clerklist_when_given_name() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan", "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);
        ResultActions result = mockMvc.perform(get("/clerklist").param("name", "fan"));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[1].username", is("username")));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[0].username", is("use8855me")));
        manageRepository.deleteAll();
        parkingBoyRepository.deleteAll();

    }

    @Test
    public void should_return_clerk_list_message_when_get_to_clerklist_when_given_phone() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan", "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);
        ResultActions result = mockMvc.perform(get("/clerklist").param("phone", "131"));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[1].username", is("username")));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[0].username", is("use8855me")));
        manageRepository.deleteAll();
        parkingBoyRepository.deleteAll();

    }

    @Test
    public void should_return_clerk_list_message_when_get_to_clerklist_when_given_email() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan", "13192269125", 1, "953181215@qq.com", new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan", "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);
        ResultActions result = mockMvc.perform(get("/clerklist").param("email", "5@q"));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[1].username", is("username")));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.data.[0].username", is("use8855me")));
        manageRepository.deleteAll();
        parkingBoyRepository.deleteAll();

    }

    @Test
    public void should_update_clerk_when_invoke_updateClerkMessage() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("pb1", "tewtwe", "test", "18365426432", 0, "5322@qq.com", null);
        ParkingBoy returnParkingBoy = parkingBoyRepository.save(parkingBoy);
        Clerk clerk = new Clerk();
        clerk.setId(returnParkingBoy.getId());
        clerk.setEmail("16547@qq.com");
        clerk.setPhoneNumber("13809438953");
        clerk.setPosition("ParkingBoy");

        ResultActions resultActions = mockMvc.perform(put("/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(clerk)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email", is("16547@qq.com")))
                .andExpect(jsonPath("$.data.phoneNumber", not("18365426432")));
        parkingBoyRepository.deleteAll();
    }
}
