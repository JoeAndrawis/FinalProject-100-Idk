package com.example.courseservice.controller;

import com.example.courseservice.model.Instructor;
import com.example.courseservice.repository.InstructorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorRepository instructorRepository;

    public InstructorController(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @PostMapping
    public ResponseEntity<Instructor> createInstructor(@Valid @RequestBody Instructor instructor) {
        Instructor savedInstructor = instructorRepository.save(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInstructor);
    }
}