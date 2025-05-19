package com.example.courseservice.exception;

public class InstructorServiceException extends RuntimeException {
    public InstructorServiceException(Long instructorId) {
        super("Instructor not found with id: " + instructorId);
    }
}
