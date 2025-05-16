package com.example.courseservice.service;

import com.example.courseservice.model.Course;
import com.example.courseservice.model.Material;

public interface CourseService {
    Course createCourse(Course course);
    Course uploadMaterial(String courseId, Material material);
    Course enrollStudent(String courseId, String studentId);
    Course getCourseById(String id);
}