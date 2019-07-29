package com.oocl.easyparkbackend.Car.Repository;

import com.oocl.easyparkbackend.Car.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Integer> {
}
