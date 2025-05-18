package com.example.courseservice.exception;

public class InstructorNotFoundException extends RuntimeException {
    public InstructorNotFoundException(String id) {
        super("Instructor not found with id: " + id);
    }
}
