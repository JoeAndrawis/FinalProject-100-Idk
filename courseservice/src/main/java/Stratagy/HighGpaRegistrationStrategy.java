package Stratagy;

import com.example.courseservice.dto.CourseType;
import com.example.courseservice.model.Course;

import client.StudentDto;

public class HighGpaRegistrationStrategy implements RegistrationStrategy {
    @Override
    public boolean canRegister(StudentDto student, Course course) {
         if (student.getGpa() > 3.5 && course.getType() == CourseType.PROGRAMMING) {
            return false;
        }
                 return true;
    }
}
