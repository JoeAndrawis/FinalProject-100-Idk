package com.example.UserMS.Factorypt;

public class Student implements UserInterface {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Double gpa;

    public Student(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gpa = 0.0;
    }

    public Student(String name, String email, String password, Double gpa) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gpa = gpa;
    }

    @Override
    public String getRole() {
        return "student";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}