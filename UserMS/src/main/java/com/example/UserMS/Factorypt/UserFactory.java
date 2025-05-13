package com.example.UserMS.Factorypt;

public class UserFactory {
    public static UserInterface createUser(String role, String name, String email, String password) {
        return switch (role.toLowerCase()) {
            case "student" -> new Student(name, email, password);
            case "admin" -> new Admin(name, email, password);
            case "instructor" -> new Instructor(name, email, password);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
