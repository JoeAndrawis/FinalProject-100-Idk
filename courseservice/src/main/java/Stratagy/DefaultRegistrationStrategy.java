package Stratagy;

import com.example.courseservice.model.Course;

import client.StudentDto;

public class DefaultRegistrationStrategy implements RegistrationStrategy {
    @Override
    public boolean canRegister(StudentDto student, Course course) {
        return true;
    }
}