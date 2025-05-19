package com.example.courseservice.model;



import com.example.courseservice.dto.CourseType;

import lombok.Data;

@Data
public class BusinessCourse extends Course {
    private String businessDomain;
    private boolean caseStudyBased;

    public BusinessCourse() {
        super();
        super.type = CourseType.BUSINESS;
    }
}
