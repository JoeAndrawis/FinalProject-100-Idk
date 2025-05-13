package com.example.UserMS.Factorypt;

public class Instructor implements UserInterface {
    private String name;
    private String email;
    private String password;

    public Instructor(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getRole() { return "instructor"; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}