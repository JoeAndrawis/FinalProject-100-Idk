package com.example.courseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients(basePackages = "client")
@EnableAsync
public class CourseserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseserviceApplication.class, args);
    }
}