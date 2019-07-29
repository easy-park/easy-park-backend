package com.oocl.easyparkbackend.Manage.Repository;


import com.oocl.easyparkbackend.Manage.Entity.Manage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManageRepository extends JpaRepository<Manage,Integer> {
}
