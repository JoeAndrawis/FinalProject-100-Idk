package com.example.UserMS.Factorypt;

public class Admin implements UserInterface {
    private String name;
    private String email;

    public Admin(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String getRole() {
        return "admin";
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

