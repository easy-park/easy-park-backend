package com.oocl.easyparkbackend.Customer.Repository;

import com.oocl.easyparkbackend.Car.Entity.Car;
import com.oocl.easyparkbackend.Customer.Entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void should_return_parkingBoy_when_getByUsernameAndPassword_given_username_and_password() {
        Customer customer = new Customer(1,"stefan","123","123","13192269125");
        customerRepository.save(customer);

        Customer customerFind = customerRepository.findByUsernameAndPassword("123", "123");

        Assertions.assertEquals(customer.getId(), customerFind.getId());
    }

}
