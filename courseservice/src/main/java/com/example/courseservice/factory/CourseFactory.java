package com.example.courseservice.factory;

import com.example.courseservice.dto.CourseType;
import com.example.courseservice.model.Course;

public interface CourseFactory {
    Course createCourse(CourseType type);
}