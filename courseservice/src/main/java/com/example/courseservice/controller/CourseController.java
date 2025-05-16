package com.example.courseservice.controller;

import com.example.courseservice.dto.CourseRequest;
import com.example.courseservice.exception.CourseNotFoundException;
import com.example.courseservice.factory.CourseFactory;
import com.example.courseservice.model.*;
import com.example.courseservice.service.CourseService;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    private final CourseFactory courseFactory;
    private final CourseService courseService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CourseController(CourseService courseService, 
                          CourseFactory courseFactory,
                          MongoTemplate mongoTemplate) {
        this.courseService = courseService;
        this.courseFactory = courseFactory;
        this.mongoTemplate = mongoTemplate;
    }

   @PostMapping
        public ResponseEntity<?> createCourse(
        @Valid @RequestBody CourseRequest request,
        BindingResult bindingResult) {
    
    if (bindingResult.hasErrors()) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
    
    try {
        Course course = courseFactory.createCourse(request.getType());
        
        course.setCode(request.getCode());
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCreditHours(request.getCreditHours());
        course.setMaxCapacity(request.getMaxCapacity());
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        
        if (course instanceof ProgrammingCourse pc) {
            pc.setProgrammingLanguage(request.getProgrammingLanguage());
            pc.setDifficultyLevel(request.getDifficultyLevel());
        } 
        else if (course instanceof LanguageCourse lc) {
            lc.setLanguage(request.getLanguage());
            lc.setProficiencyLevel(request.getProficiencyLevel());
        }
        else if (course instanceof BusinessCourse bc) {
            bc.setBusinessDomain(request.getBusinessDomain());
        }
        
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        
    } catch (Exception e) {
        return ResponseEntity.internalServerError()
            .body(Map.of("error", "Failed to create course: " + e.getMessage()));
    }
}



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