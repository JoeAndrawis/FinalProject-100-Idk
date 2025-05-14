package com.example.courseservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "materials")
public class Material {
    @Id
    private String id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotBlank(message = "URL is required")
    private String url;
    
    @NotBlank(message = "File type is required")
    private String fileType;
    
    private LocalDateTime uploadDate = LocalDateTime.now();
    
    
    private String courseId;
}