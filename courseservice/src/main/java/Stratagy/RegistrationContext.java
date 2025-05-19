package Stratagy;

import com.example.courseservice.model.Course;

import client.StudentDto;

public class RegistrationContext {

    public boolean registerStudent(StudentDto student, Course course) {
        RegistrationStrategy strategy;

        if (student.getGpa() > 3.5) {
            strategy = new HighGpaRegistrationStrategy();
        } else {
            strategy = new DefaultRegistrationStrategy();
            
        }

        return strategy.canRegister(student, course);
    }
}
