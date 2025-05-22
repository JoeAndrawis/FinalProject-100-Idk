# 🎓 Microservices Education Platform

A microservices-based backend system built with Spring Boot, Docker, and MongoDB/PostgreSQL to manage courses, users, notifications, and questions.

## 🧱 Services Overview

| Service         | Description                                  | Port  |
|-----------------|----------------------------------------------|--------|
| `user-service`  | Manages user registration, login, and roles  | 8080   |
| `course-service`| Handles course creation and enrollment       | 8081   |
| `notification-service` | Sends email/SMS notifications         | 8083   |
| `question-service` | Manages questions and assessments         | 8084   |

Each service runs independently and communicates via REST APIs.

---

## 🗂 Project Structure

### FinalProject-100-Idk/
├── apigateway/
├── courseservice/
├── NotificationService/
├── QuestionMS/
├── UserMS/
├── docker-compose.yaml
└── README.md 

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose

###  Running with Docker
###  Technologies Used
-Java, Spring Boot

-MongoDB, PostgreSQL

-Docker, Docker Compose

-Spring Cloud Gateway

-JWT Authentication

-REST APIs
### Authentication
JWT-based authentication system

Login and registration handled by UserMS

Auth token must be included in requests to protected endpoints

Role-based access for students, instructors, admin.
### Course Service API Documentation
Base URL
bash
Copy
Edit
http://localhost:8081/api/courses

### Create a New Course
Endpoint:

bash
POST /api/courses

### Description:
Creates a new course based on type (ProgrammingCourse, LanguageCourse, BusinessCourse).

Request Body (JSON):

json
{
  "type": "PROGRAMmING",
  "code": "CS605",
  "title": "Intro to Java",
  "description": "Basics of Java programming",
  "creditHours": 3,
  "maxCapacity": 30,
  "programmingLanguage": "Java",
  "difficultyLevel": "Beginner"
}
![image](https://github.com/user-attachments/assets/95bdc318-b151-493b-acde-34cf664f3ada)
### 2. Upload Course Material
Endpoint:

bash

POST /api/courses/{courseId}/materials
Description:
Uploads a material (PDF, link, etc.) to a specific course.

Request Body (JSON):

json
{
  "title": "Lecture 1 Slides",
  "type": "pdf",
  "url": "https://example.com/lecture1.pdf",
  "fileType" : "pdf" 
}
![image](https://github.com/user-attachments/assets/18da892b-4122-49c3-abbe-1f33896c9f25)

### Assign Instructor to Course
Endpoint:

bash
POST /api/courses/{courseId}/instructors/{instructorId} 
![image](https://github.com/user-attachments/assets/668559ba-17a2-458f-9c62-15c5deaa978a)

### Enroll Student in Course
Endpoint:

POST /api/courses/{courseId}/students/{studentId}
![image](https://github.com/user-attachments/assets/f022fed2-69d8-4eae-a5d3-aef0d164b7be) 

###  Design Patterns in CourseService
The CourseService microservice leverages two fundamental design patterns to enhance scalability, maintainability, and flexibility:

Factory Pattern: Facilitates the creation of different course types based on input parameters.

Strategy Pattern: Enables the encapsulation and interchangeability of algorithms or behaviors, particularly for course-specific operations.

### Factory Pattern
Purpose: The Factory Pattern provides a way to create objects without specifying the exact class of the object to be created. It delegates the instantiation logic to a factory class, promoting loose coupling and adherence to the Open/Closed Principle.

Implementation in CourseService:

CourseFactory: This class contains the logic to instantiate different subclasses of the Course base class (ProgrammingCourse, LanguageCourse, BusinessCourse) based on the type provided in the request. 

###  Strategy Pattern

Strategy Pattern – Conditional Enrollment Logic

Use Case in CourseService:
For Programming Courses, students with a GPA greater than 3.5 are not allowed to enroll. This rule is encapsulated using the Strategy pattern.

###  Why This Matters
Using the Strategy pattern for enrollment rules:

Keeps business logic isolated from controller/service layers

Makes it easy to add new rules (e.g., language level checks, business domain validation)

Avoids large if-else chains

Supports unit testing each rule independently
### uture Example Strategies
LanguageCourseEnrollmentStrategy: Prevent beginner-level students from taking advanced courses.

BusinessCourseEnrollmentStrategy: Require previous experience or course completion.

