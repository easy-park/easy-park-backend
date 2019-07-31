package com.oocl.easyparkbackend.Manager.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.Manage.Controller.ManageController;
import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.Manage.Service.ManageService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ManageController.class)
public class ManagerControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ManageService manageService;

    @Test
    public void should_return_manager_when_invoke_login_given_email_and_password() throws Exception {
        Manage manage = new Manage();
        manage.setEmail("1052817017@qq.com");
        manage.setPassword("123");

        when(manageService.login(any())).thenReturn(anyString());
        ResultActions resultActions = mvc.perform(post("/manager/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(manage)));

        resultActions.andExpect(status().isOk());
        verify(manageService).login(any());
    }

}
