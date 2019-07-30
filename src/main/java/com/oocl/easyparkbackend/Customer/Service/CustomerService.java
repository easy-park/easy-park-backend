package com.oocl.easyparkbackend.Customer.Service;

import com.itmuch.lightsecurity.jwt.JwtOperator;
import com.itmuch.lightsecurity.jwt.User;
import com.itmuch.lightsecurity.jwt.UserOperator;
import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.Customer.Exception.NotFindCustomerExcepetion;
import com.oocl.easyparkbackend.Customer.Repository.CustomerRepository;
import com.oocl.easyparkbackend.ParkingBoy.Exception.UserNameOrPasswordErrorException;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import com.oocl.easyparkbackend.ParkingOrder.Repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @Autowired
    private JwtOperator jwtOperator;

    @Autowired
    private UserOperator userOperator;

    public String login(Customer customer) {
        Customer customerFind = new Customer();
        if (customer.getUsername() != null && customer.getPassword() != null) {
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(customer.getPassword().getBytes());
            customerFind = customerRepository.findByUsernameAndPassword(customer.getUsername(), md5DigestAsHex);
        } else if (customer.getPhone() != null && customer.getPassword() != null) {
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(customer.getPassword().getBytes());
            customerFind = customerRepository.findByPhoneAndPassword(customer.getPhone(), md5DigestAsHex);
        }
        if (customerFind != null) {
            User user = User.builder()
                    .id(customerFind.getId())
                    .username(customerFind.getUsername())
                    .roles(Arrays.asList("customer"))
                    .build();
            return jwtOperator.generateToken(user);
        }
        throw new UserNameOrPasswordErrorException();
    }

    public Customer findById() {
        User user = userOperator.getUser();
        Customer customer = customerRepository.findById(user.getId()).orElse(null);
        if (customer != null) {
            return customer;
        }
        throw new NotFindCustomerExcepetion();
    }

    public Customer save(Customer customer) {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(customer.getPassword().getBytes());
        customer.setPassword(md5DigestAsHex);
        return customerRepository.save(customer);
    }

    public List<ParkingOrder> getHistoryOrder() {
        User user = userOperator.getUser();
        Customer customer = customerRepository.findById(user.getId()).orElse(null);
        if (customer == null) {
            throw new NotFindCustomerExcepetion();
        }
        List<ParkingOrder> parkingOrders = new ArrayList<>();
        parkingOrders.addAll(parkingOrderRepository.findAllByCustomerAndStatus(customer, 6));
        return parkingOrders;
    }
}
