package com.oocl.easyparkbackend.ParkingOrder.Repository;

import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingOrder.Entity.ParkingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ParkingOrderRepository extends JpaRepository<ParkingOrder,String> {

    List<ParkingOrder> findAllByStatus( int status);
    
    List<ParkingOrder> findAllByParkingBoyAndStatus(ParkingBoy parkingBoy, int status);

    List<ParkingOrder> findAllByCustomerAndStatus(Customer customer, int status);

    ParkingOrder findByCarNumberAndEndTime(String number, Timestamp timestamp);

    List<ParkingOrder> findByCarNumberLike(String carNumber);

    List<ParkingOrder> findAllByParkingBoyAndStatusIsLessThan(ParkingBoy parkingBoy, int status);
}
