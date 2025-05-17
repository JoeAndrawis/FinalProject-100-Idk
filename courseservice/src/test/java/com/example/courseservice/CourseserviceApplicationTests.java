package com.example.courseservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.courseservice.service.CourseService;

import client.UserServiceClient;

@SpringBootTest
class CourseserviceApplicationTests {

    @MockBean
    private UserServiceClient userServiceClient;
    
    @Autowired
    private CourseService courseService;

    @Test
    void contextLoads() {
    }
}