package com.example.courseservice.controller;

import com.example.courseservice.exception.CourseNotFoundException;
import com.example.courseservice.model.*;
import com.example.courseservice.service.CourseService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController { 

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }
    @Autowired
    private MongoTemplate mongoTemplate;



   @PostMapping("/{courseId}/materials")
public ResponseEntity<?> uploadMaterial(
        @PathVariable String courseId,
        @Valid @RequestBody Material material,
        BindingResult bindingResult) {
    
    if (bindingResult.hasErrors()) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
    
    try {
        Course updatedCourse = courseService.uploadMaterial(courseId, material);
        return ResponseEntity.ok(updatedCourse);
    } catch (CourseNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}

    @PostMapping("/{courseId}/instructors/{instructorId}")
    public ResponseEntity<Course> assignInstructor(
            @PathVariable String courseId,
            @PathVariable String instructorId) {
        
        Course updatedCourse = courseService.assignInstructor(courseId, instructorId);
        return ResponseEntity.ok(updatedCourse);
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<Course> enrollStudent(
            @PathVariable String courseId,
            @PathVariable String studentId) {
        
        Course updatedCourse = courseService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable String courseId) {
        Course course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(course);
    }
}