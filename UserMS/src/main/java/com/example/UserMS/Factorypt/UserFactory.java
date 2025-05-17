package com.example.UserMS.Factorypt;

public class UserFactory {
    //Existing method (no GPA)
    public static UserInterface createUser(String role, String name, String email, String password) {
        return switch (role.toLowerCase()) {
            case "student" -> new Student(name, email, password); // default GPA = 0.0
            case "admin" -> new Admin(name, email, password);
            case "instructor" -> new Instructor(name, email, password);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }

    // New overloaded method for student with GPA
    public static UserInterface createUser(String role, String name, String email, String password, double gpa) {
        if (role.equalsIgnoreCase("student")) {
            return new Student(name, email, password, gpa);
        } else {
            //old method
            return createUser(role, name, email, password);
        }
    }
}