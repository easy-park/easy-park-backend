package com.oocl.easyparkbackend.Customer.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmuch.lightsecurity.jwt.JwtOperator;
import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.Customer.Repository.CustomerRepository;
import com.oocl.easyparkbackend.Customer.Service.CustomerService;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CustomerIntegration {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService service;

    @Autowired
    private JwtOperator jwtOperator;

    @BeforeEach
    void setup(){ mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); }

    @Test
    public void should_return_token_when_login_given_customer() throws Exception {
        customerRepository.deleteAll();
        Customer customer = new Customer();
        customer.setPhone("18229784935");
        customer.setPassword("12345");
        customer.setName("amy");
        customer.setUsername("ouyangamy");
        customerRepository.save(customer);
        Customer postCustomer = new Customer();
        postCustomer.setPhone("18229784935");
        postCustomer.setPassword("12345");

        ResultActions resultActions = mockMvc.perform(post("/customer/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postCustomer)));

        resultActions.andExpect(status().isOk());
        customerRepository.deleteAll();
    }

    @Test
    public void should_return_UserNameOrPasswordErrorException_when_login_given_error_customer() throws Exception {
        Customer customer = new Customer();
        customer.setPhone("18229784934");
        customer.setPassword("12345");
        customer.setName("amy");
        customer.setUsername("ouyangamy");
        customerRepository.save(customer);
        Customer postCustomer = new Customer();
        postCustomer.setPhone("18229784934");
        postCustomer.setPassword("14444");

        ResultActions resultActions = mockMvc.perform(post("/customer/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postCustomer)));

        resultActions.andExpect(status().isOk());
        assertThrows(UserNameOrPasswordErrorException.class, () -> service.login(postCustomer));
    }

    @Test
    public void should_return_customerInfo_when_invoke_getUserInfo() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/customer"));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void should_return_user_history_orders() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/customer/historyorder"));
        resultActions.andExpect(status().isOk());
    }

}
