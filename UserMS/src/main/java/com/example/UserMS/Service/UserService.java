package com.example.UserMS.Service;

public interface UserService {
    Object signUp(Object entity, String role);
    String login(String email);
    String logout();
    Object viewProfile(String email);
    Object updateProfile(Object entity, String role);
    void deleteUser(String email);
    Object findUserByEmail(String email);
    Object findUserByName(String name);
    Object verifyUser(String email);

    Object findUserById(Long id);
    Object findStudentById(Long id);
    Object findInstructorById(Long id);

}
