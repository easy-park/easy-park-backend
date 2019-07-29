package com.oocl.easyparkbackend.Manage.Repository;


import com.oocl.easyparkbackend.Manage.Entity.Manage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManageRepository extends JpaRepository<Manage,Integer> {
    List<Manage> findAllByNameLike(String name);

    List<Manage> findAllByEmailLike(String email);

    List<Manage> findAllByPhoneNumberLike(String phone);
}
