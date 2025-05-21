# 🎓 Microservices Education Platform

A microservices-based backend system built with Spring Boot, Docker, and MongoDB/PostgreSQL to manage courses, users, notifications, and questions.

## 🧱 Services Overview

| Service         | Description                                  | Port  |
|-----------------|----------------------------------------------|--------|
| `user-service`  | Manages user registration, login, and roles  | 8081   |
| `course-service`| Handles course creation and enrollment       | 8082   |
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

### 🐳 Running with Docker

To start the entire system:

```bash
docker-compose up --build
