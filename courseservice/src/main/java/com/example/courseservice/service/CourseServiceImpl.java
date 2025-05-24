package com.example.courseservice.service;

import com.example.courseservice.exception.*;
import com.example.courseservice.model.*;
import com.example.courseservice.repository.*;
import Stratagy.RegistrationContext; 
import client.InstructorDto;
import client.StudentDto;
import client.UserServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final MaterialRepository materialRepository;
    private final UserServiceClient userServiceClient;
     @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, 
                            UserServiceClient userServiceClient,MaterialRepository materialRepository) {
        this.courseRepository = courseRepository;
        this.materialRepository = materialRepository;
        this.userServiceClient = userServiceClient;
    }
    @Override
    public Course createCourse(Course course) {
        if (courseRepository.existsByCode(course.getCode())) {
            throw new DuplicateCourseException("Course code already exists");
        }
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }

    @Override
    public Course uploadMaterial(String courseId, Material material) {
        material.setCourseId(courseId);
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        
        Material savedMaterial = materialRepository.save(material);
        
        course.getMaterialIds().add(savedMaterial.getId());
        return courseRepository.save(course);
    }

   

    @Transactional
    public Course assignStudentToCourse(String courseId, StudentDto studentDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    
        RegistrationContext context = new RegistrationContext();
    
        if (!context.registerStudent(studentDto, course)) {
            throw new IllegalStateException("Student does not meet registration criteria");
        }
    
        String studentIdStr = String.valueOf(studentDto.getId());
    
        if (!course.getStudentIds().contains(studentIdStr)) {
            course.getStudentIds().add(studentIdStr);
            course.setCurrentEnrollment(course.getCurrentEnrollment() + 1);
            course.setUpdatedAt(LocalDateTime.now());
            course = courseRepository.save(course);
        }
    
        return course;
    }
        


    @Override
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }
    
    @Transactional
    public Course assignInstructor(String courseId, InstructorDto instructorDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    
        InstructorDto newInstructor = new InstructorDto(instructorDto.getId(), instructorDto.getName());
    
        if (!course.getInstructors().contains(newInstructor)) {
            course.getInstructors().add(newInstructor);
            course.setUpdatedAt(LocalDateTime.now());
            course = courseRepository.save(course);
        }
    
        return course;
    }
    
}


    
