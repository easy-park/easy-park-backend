package com.oocl.easyparkbackend.Customer.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.Customer.Service.CustomerService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void should_return_token_when_invoke_login_given_userName_and_password() throws Exception {


        when(customerService.login(any())).thenReturn("123");
        ResultActions resultActions = mvc.perform(post("/customer/login")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n"+ "\"username\":\"123\",\n" + "\"password\":\"1234\"\n" + "}"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void should_return_parkingBoy_when_invoke_find_parkingBoy() throws Exception {
        Customer customer = new Customer();
        customer.setId(123);
        customer.setUsername("15574957517");
        customer.setPassword("123");

        when(customerService.findById()).thenReturn(customer);

        ResultActions resultActions = mvc.perform(get("/customer"));

        resultActions.andExpect(status().isOk());
        verify(customerService).findById();
    }

    @Test
    public void should_return_customer_when_invoke_register() throws Exception {
        Customer customer = new Customer();
        customer.setId(123);
        customer.setUsername("15574957517");
        customer.setPassword("123");

        when(customerService.save(any())).thenReturn(customer);

        ResultActions resultActions = mvc.perform(post("/customer/register")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                        "{\n"+
                                "\"username\":\"12345\",\n" +
                                "\"password\":\"123\",\n" +
                                "\"name\":\"123\"\n" +
                        "}"
                ));

        resultActions.andExpect(status().isOk());
        verify(customerService).save(any());
    }

}
