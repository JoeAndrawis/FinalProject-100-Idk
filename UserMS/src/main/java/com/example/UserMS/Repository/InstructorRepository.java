package com.example.UserMS.Repository;

import com.example.UserMS.Models.InstructorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorsEntity, Long> {
    Optional<InstructorsEntity> findByEmail(String email);
    Optional<InstructorsEntity> findByName(String name);
}