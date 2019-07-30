package com.oocl.easyparkbackend.Employee.Repository;

import com.oocl.easyparkbackend.Employee.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
