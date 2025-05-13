package com.example.UserMS.Factorypt;

public class Student implements UserInterface {
    private String name;
    private String email;
    private String password;

    public Student(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getRole() { return "student"; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
