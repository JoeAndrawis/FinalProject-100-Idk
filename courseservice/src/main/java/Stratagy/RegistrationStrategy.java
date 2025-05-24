package Stratagy;

import com.example.courseservice.model.Course;

import client.StudentDto;

public interface RegistrationStrategy {
    boolean canRegister(StudentDto student, Course course);
}
