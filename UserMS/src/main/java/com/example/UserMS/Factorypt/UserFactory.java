package com.example.UserMS.Factorypt;

public class UserFactory {
    public static UserInterface createUser(String role, String name, String email) {
        switch (role.toLowerCase()) {
            case "student":
                return new Student(name, email);
            case "admin":
                return new Admin(name, email);
            case "instructor":
                return new Instructor(name, email);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}

