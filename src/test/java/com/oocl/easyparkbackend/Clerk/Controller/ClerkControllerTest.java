package com.oocl.easyparkbackend.Clerk.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.Employee.Entity.Clerk;
import com.oocl.easyparkbackend.Employee.Entity.Employee;
import com.oocl.easyparkbackend.Employee.Service.ClerkService;
import com.oocl.easyparkbackend.Employee.Controller.ClerkController;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.common.vo.ClerkPosition;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClerkController.class)
public class ClerkControllerTest {

    @MockBean
    private ClerkService clerkService;

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @BeforeEach
    void setup(){ mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); }
    @Test
    public void should_return_clerk_list_when_get_to_clerks() throws Exception {
        List<Clerk> clerkList = new ArrayList<>();
        when(clerkService.showAllClerkMessage()).thenReturn(clerkList);

        ResultActions result = mockMvc.perform(get("/clerks"));

        result.andExpect(status().isOk());
    }

    @Test
    public void should_return_clerk_message_when_get_to_clerkList_given_name() throws Exception {
        List<Clerk> clerkList = new ArrayList<>();
        when(clerkService.findClerkMessageByName(anyString())).thenReturn(clerkList);

        ResultActions result = mockMvc.perform(get("/clerklist").param("name","lay"));

        result.andExpect(status().isOk());
    }
    @Test
    public void should_return_clerk_message_when_get_to_clerkList_given_id() throws Exception {
        List<Clerk> clerkList = new ArrayList<>();
        when(clerkService.findClerkMessageById(anyInt())).thenReturn(clerkList);

        ResultActions result = mockMvc.perform(get("/clerklist").param("id","111"));

        result.andExpect(status().isOk());
    }
    @Test
    public void should_return_clerk_message_when_get_to_clerkList_given_email() throws Exception {
        List<Clerk> clerkList = new ArrayList<>();
        when(clerkService.findClerkMessageByEmail(anyString())).thenReturn(clerkList);

        ResultActions result = mockMvc.perform(get("/clerklist").param("email","lay@126.com"));

        result.andExpect(status().isOk());
    }
    @Test
    public void should_return_clerk_message_when_get_to_clerkList_given_phone() throws Exception {
        List<Clerk> clerkList = new ArrayList<>();
        when(clerkService.findClerkMessageByPhone(anyString())).thenReturn(clerkList);

        ResultActions result = mockMvc.perform(get("/clerklist").param("phone","lay"));

        result.andExpect(status().isOk());
    }

    @Test
    public void should_update_clerk_message_when_invoke_update_given_clerk() throws Exception {
        Manage manage = new Manage(1, "manager1", "password", "amy", "18432342334", 1, "123@qq.com");
        Clerk clerk = new Clerk();
        clerk.setId(1);
        clerk.setPosition(ClerkPosition.MANAGER);
        clerk.setPhoneNumber("18432342334");
        clerk.setEmail("123@qq.com");

        when(clerkService.update(any())).thenReturn(manage);
        ResultActions resultActions = mockMvc.perform(put("/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(clerk)));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email", Matchers.is("123@qq.com")));
    }

    @Test
    public void should_update_clerk_status_when_invoke_updateClerkStatus() throws Exception {
        Employee employee = new Employee("username", "password", "name", "car2344", 1, "14234@qq.com");
        employee.setId(4);
        Clerk clerk = new Clerk();
        clerk.setId(4);
        clerk.setStatus(1);
        clerk.setPosition("Employee");

        when(clerkService.updateClerkStatus(any())).thenReturn(employee);
        ResultActions resultActions = mockMvc.perform(put("/clerks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(clerk)));

        resultActions.andExpect(status().isOk());
    }

}
