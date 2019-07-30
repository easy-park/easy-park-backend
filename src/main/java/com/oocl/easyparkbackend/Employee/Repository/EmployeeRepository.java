package com.oocl.easyparkbackend.Employee.Repository;

import com.oocl.easyparkbackend.Employee.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByNameLike(String name);

    List<Employee> findByEmailLike(String email);

    List<Employee> findByPhoneNumberLike(String phone);

}
