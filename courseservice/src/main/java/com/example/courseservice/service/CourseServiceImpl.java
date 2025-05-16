package com.example.courseservice.service;

import com.example.courseservice.exception.*;
import com.example.courseservice.model.*;
import com.example.courseservice.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final MaterialRepository materialRepository;

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

   

    @Override
    public Course enrollStudent(String courseId, String studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
        
        if (course.getCurrentEnrollment() >= course.getMaxCapacity()) {
            throw new CourseFullException("Course has reached maximum capacity");
        }
        
        if (!course.getStudentIds().contains(studentId)) {
            course.getStudentIds().add(studentId);
            course.setCurrentEnrollment(course.getCurrentEnrollment() + 1);
            course.setUpdatedAt(LocalDateTime.now());
            return courseRepository.save(course);
        }
        return course;
    }

    @Override
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }
}