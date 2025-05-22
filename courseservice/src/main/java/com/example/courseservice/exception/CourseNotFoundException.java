package com.example.courseservice.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String id) {
        super("Course not found with id: " + id);
    }
}


