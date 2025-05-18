package com.example.UserMS.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class StudentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @DecimalMin(value = "0.7", message = "GPA must be at least 0.7")
    @Max(value = 5, message = "GPA must not exceed 5.0")
    private double gpa;

    // Constructors
    public StudentsEntity() {
        this.gpa = 0.0;
    }

    public StudentsEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gpa = 0.0;
    }

    public StudentsEntity(String name, String email, String password, double gpa) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gpa = gpa;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}