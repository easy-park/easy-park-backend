package com.oocl.easyparkbackend.Clerk.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.Employee.Entity.Clerk;
import com.oocl.easyparkbackend.Employee.Exception.ClerkEmailAndPhoneNumberNotNullException;
import com.oocl.easyparkbackend.Employee.Exception.ClerkIdErrorException;
import com.oocl.easyparkbackend.Employee.Service.ClerkService;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Repository.ManageRepository;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Repository.ParkingBoyRepository;
import com.oocl.easyparkbackend.common.vo.ClerkPosition;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.is;

import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class ClerkIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ManageRepository manageRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ClerkService clerkService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private ParkingBoy createParkingBoy() {
        ParkingBoy parkingBoy = new ParkingBoy();
        parkingBoy.setUsername("carterchen");
        parkingBoy.setName("carter");
        parkingBoy.setPassword("123456");
        parkingBoy.setPhoneNumber("13192269125");
        parkingBoy.setStatus(1);
        parkingBoy.setEmail("carter@qq.com");
        parkingBoy.setParkingLotList(Collections.emptyList());
        return parkingBoy;
    }

    private Manage createManage() {
        Manage manage = new Manage();
        manage.setUsername("username");
        manage.setPassword("password");
        manage.setName("name");
        manage.setPhoneNumber("13956849856");
        manage.setStatus(1);
        manage.setEmail("name@163.com");
        return manage;
    }

    @Test
    void should_return_real_clerk_list_when_get_to_clerks() throws Exception {
        ParkingBoy parkingBoy = parkingBoyRepository.save(createParkingBoy());
        Manage manage = manageRepository.save(createManage());

        ResultActions result = mockMvc.perform(get("/clerks"));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.iterableWithSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].username",
                        Matchers.isOneOf(parkingBoy.getUsername(), manage.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].phoneNumber",
                        Matchers.isOneOf(parkingBoy.getPhoneNumber(), manage.getPhoneNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name",
                        Matchers.isOneOf(parkingBoy.getName(), manage.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].status",
                        Matchers.is(parkingBoy.getStatus())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].email",
                        Matchers.isOneOf(parkingBoy.getEmail(), manage.getEmail())));
    }

    @Test
    void should_return_real_clerk_list_when_get_to_clerks_given_10boys_and_5Manages() throws Exception {
        parkingBoyRepository.deleteAll();
        manageRepository.deleteAll();
        for (int i = 0; i < 10; i++) {
            ParkingBoy parkingBoy = new ParkingBoy("username" + i, "123456" + i, "name" + i,
                    "13192269125" + i, 1, i + "953181215@qq.com", Collections.emptyList());
            parkingBoyRepository.save(parkingBoy);
        }
        for (int i = 0; i < 5; i++) {
            Manage manage = new Manage(null, "use8855me" + i, "199529" + i, "st77fan" + i,
                    "13192545625" + i, 1, "953188555@qq.com+i");
            manageRepository.save(manage);
        }

        ResultActions result = mockMvc.perform(get("/clerks"));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.iterableWithSize(15)));
        for (int i = 0; i < 5; i++) {
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.[" + i + "].username", is("use8855me" + i)))
                    .andExpect(jsonPath("$.data.[" + i + "].position", is("Manage")));
        }
        for (int i = 5; i < 15; i++) {
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.[" + i + "].username", is("username" + (i - 5))))
                    .andExpect(jsonPath("$.data.[" + i + "].position", is("ParkingBoy")));
        }
    }

    @Test
    void should_return_clerk_list_when_query_clerklist_when_given_a_name() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan",
                "13192269125", 1, "953181215@qq.com", new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        Manage manage = new Manage(null, "use8855me", "199529", "st77fan",
                "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);

        ResultActions result = mockMvc.perform(get("/clerklist").param("name", "efan"));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.iterableWithSize(1)))
                .andExpect(jsonPath("$.data.[0].username", Matchers.is(parkingBoy.getUsername())));
    }

    @Test
    void should_return_clerk_list_message_when_get_to_clerklist_when_given_phone() throws Exception {
        parkingBoyRepository.deleteAll();
        manageRepository.deleteAll();
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan",
                "13192269125", 1, "953181215@qq.com", new ArrayList<>());
        parkingBoyRepository.save(parkingBoy);
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan",
                "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);

        ResultActions result = mockMvc.perform(get("/clerklist").param("phone", "69125"));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.iterableWithSize(1)))
                .andExpect(jsonPath("$.data.[0].username", Matchers.is(parkingBoy.getUsername())));
    }

    @Test
    void should_return_clerk_list_message_when_get_to_clerklist_when_given_email() throws Exception {
        parkingBoyRepository.deleteAll();
        manageRepository.deleteAll();
        ParkingBoy parkingBoy = new ParkingBoy("username", "199729", "stefan",
                "13192269125", 1, "953181215@qq.com", Collections.emptyList());
        parkingBoyRepository.save(parkingBoy);
        Manage manage = new Manage(1, "use8855me", "199529", "st77fan",
                "13192545625", 1, "953188555@qq.com");
        manageRepository.save(manage);

        ResultActions result = mockMvc.perform(get("/clerklist").param("email", "55@qq"));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.iterableWithSize(1)))
                .andExpect(jsonPath("$.data.[0].username", Matchers.is(manage.getUsername())));
    }

    @Test
    void should_update_clerk_when_invoke_updateClerkMessage() throws Exception {
        ParkingBoy parkingBoy = new ParkingBoy("pb1", "tewtwe", "test",
                "18365426432", 0, "5322@qq.com", null);
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
                .andExpect(jsonPath("$.data.email", Matchers.is(clerk.getEmail())))
                .andExpect(jsonPath("$.data.phoneNumber", Matchers.is(clerk.getPhoneNumber())));
    }

    @Test
    void should_throw_ClerkEmailAndPhoneNumberNotNullException_when_invoke_update_given_null_email_and_phone() throws Exception {
        Clerk clerk = new Clerk();
        clerk.setPosition("ParkingBoy");

        ResultActions resultActions = mockMvc.perform(put("/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(clerk)));

        Exception exception = resultActions.andExpect(jsonPath("$.msg", is("clerk's email and phone number can't be null.")))
                .andReturn().getResolvedException();
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(ClerkEmailAndPhoneNumberNotNullException.class, exception.getClass());
    }

    @Test
    void should_throw_ClerkIdErrorException_when_invoke_update_given_a_id_not_exists() throws Exception {
        Clerk clerk = new Clerk();
        clerk.setPosition(ClerkPosition.MANAGER);
        clerk.setEmail("email@mail.com");
        clerk.setPhoneNumber("15968466589");
        clerk.setId(-1);

        ResultActions resultActions = mockMvc.perform(put("/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(clerk)));

        Exception exception = resultActions.andReturn().getResolvedException();
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(ClerkIdErrorException.class, exception.getClass());
    }
}
