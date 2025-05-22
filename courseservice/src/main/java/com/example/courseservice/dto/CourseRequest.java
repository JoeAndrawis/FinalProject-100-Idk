package com.example.courseservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CourseRequest {
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

    @Min(1)
    private int maxCapacity;

    @NotNull
    private CourseType type;

    private String programmingLanguage;
    private String difficultyLevel;

    private String language;
    private String proficiencyLevel;

    private String businessDomain;
}