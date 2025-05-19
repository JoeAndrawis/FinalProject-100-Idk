package com.example.courseservice.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.courseservice.dto.CourseType;

import lombok.Data;

@Data
public class ProgrammingCourse extends Course {
    private String programmingLanguage;
    private String difficultyLevel;

    public ProgrammingCourse() {
        super(); 
        super.type = CourseType.PROGRAMMING; 
    }
}