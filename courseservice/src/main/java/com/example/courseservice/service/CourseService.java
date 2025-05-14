package com.example.courseservice.service;

import com.example.courseservice.model.Course;
import com.example.courseservice.model.Material;
import com.example.courseservice.model.Instructor;

public interface CourseService {
    Course createCourse(Course course);
    Course uploadMaterial(String courseId, Material material);
    Course assignInstructor(String courseId, String instructorId);
    Course enrollStudent(String courseId, String studentId);
    Course getCourseById(String id);
}