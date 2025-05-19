package com.example.courseservice.service;

import com.example.courseservice.model.Course;
import com.example.courseservice.model.Material;

import client.InstructorDto;
import client.StudentDto;

public interface CourseService {
    Course createCourse(Course course);
    Course uploadMaterial(String courseId, Material material);
    Course assignStudentToCourse(String courseId, StudentDto studentDto);
    Course getCourseById(String id);
    Course assignInstructor(String courseId, InstructorDto instructorDto);
    

}