package com.example.UserMS.Factorypt;

public class Instructor implements UserInterface {
    private String name;
    private String email;

    public Instructor(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String getRole() {
        return "instructor";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }
}

