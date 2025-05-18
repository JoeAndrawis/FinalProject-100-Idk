package com.example.UserMS.Factorypt;

public interface UserInterface {
    String getRole();
    String getName();
    String getEmail();
    String getPassword();

    Long getId();         // new
    void setId(Long id);  // new

    default double getGpa() {
        return 0.0; // default for non-students
    }
}