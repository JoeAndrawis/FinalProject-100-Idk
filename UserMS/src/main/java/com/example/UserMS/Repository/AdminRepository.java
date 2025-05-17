package com.example.UserMS.Repository;

import com.example.UserMS.Models.AdminsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminsEntity, Long> {
    Optional<AdminsEntity> findByEmail(String email);
    Optional<AdminsEntity> findByName(String name);
}