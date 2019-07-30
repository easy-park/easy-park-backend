package com.oocl.easyparkbackend.Employee.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyparkbackend.Employee.Entity.Employee;
import com.oocl.easyparkbackend.Employee.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_add_employee_invoke_addEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("billy");
        employee.setEmail("billy@qq.com");
        employee.setPassword("12345");
        employee.setPhoneNumber("12345");

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        ResultActions resultActions = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.data.name",is("billy")));

    }

}
