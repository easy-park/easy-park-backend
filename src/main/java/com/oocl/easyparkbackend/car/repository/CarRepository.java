package com.oocl.easyparkbackend.car.repository;

import com.oocl.easyparkbackend.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Integer> {
}
