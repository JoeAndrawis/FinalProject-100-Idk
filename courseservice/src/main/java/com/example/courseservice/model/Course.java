package com.example.courseservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.courseservice.dto.CourseType;

import client.InstructorDto;
import client.StudentDto;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

@Document(collection = "courses")
@Data
public class Course {
    @Id
    private String id;

    @NotBlank(message = "Course code is required")
    @Size(min = 3, max = 10)
    private String code;

    @NotBlank(message = "Title is required")
    @Size(max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @Min(1) @Max(6)
    private int creditHours;

    private List<String> materialIds = new ArrayList<>();
    private List<InstructorDto> instructors = new ArrayList<>();
    private List<Long> instructorIds = new ArrayList<>();
    private List<String> studentIds = new ArrayList<>();
    protected CourseType type; 
    private List<StudentDto> students = new ArrayList<>();

    @Min(1) @Max(3)
    private int maxCapacity;
    private int currentEnrollment = 0;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}