package com.oocl.easyparkbackend.ParkingBoy.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingBoy.Service.ParkingBoyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
}
