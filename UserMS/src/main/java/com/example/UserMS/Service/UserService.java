package com.example.UserMS.Service;

import com.example.UserMS.rabbitmq.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;

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
    String createPost(Long id);

}
