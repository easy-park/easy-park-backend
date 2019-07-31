package com.oocl.easyparkbackend.Manage.Repository;


import com.oocl.easyparkbackend.Manage.Entity.Manage;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManageRepository extends JpaRepository<Manage,Integer> {
    List<Manage> findAllByNameLike(String name);

    List<Manage> findAllByEmailLike(String email);

    List<Manage> findAllByPhoneNumberLike(String phone);

    Optional<Manage> getByEmailAndPassword(String email, String password);

    Optional<Manage> getByPhoneNumberAndPassword(String phoneNumber, String password);

    Optional<Manage> getByUsernameAndPassword(String username, String password);
}
