package com.example.courseservice.factory;

import com.example.courseservice.dto.CourseType;
import com.example.courseservice.model.*;
import org.springframework.stereotype.Component;

@Component
public class CourseFactoryImpl implements CourseFactory {

    @Override
    public Course createCourse(CourseType type) {
        return switch (type) {
            case PROGRAMMING -> new ProgrammingCourse();
            case LANGUAGE -> new LanguageCourse();
            case BUSINESS -> new BusinessCourse();
        };
    }
}