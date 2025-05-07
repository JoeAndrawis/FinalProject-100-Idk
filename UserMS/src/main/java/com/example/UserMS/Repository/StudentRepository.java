package com.example.UserMS.Repository;

import com.example.UserMS.Models.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentsEntity, Long> {
    Optional<StudentsEntity> findByEmail(String email);
    Optional<StudentsEntity> findByName(String email);
}




