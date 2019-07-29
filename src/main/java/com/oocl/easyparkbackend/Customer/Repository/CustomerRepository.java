package com.oocl.easyparkbackend.Customer.Repository;

import com.oocl.easyparkbackend.Customer.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByUsernameAndPassword(String username, String password);

    Customer findByPhoneAndPassword(String phone, String password);

}
