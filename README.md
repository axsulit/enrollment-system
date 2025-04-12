# Enrollment System

A microservices-based university enrollment system that manages student enrollment, course registration, grade management, and user authentication.

## Features

### Authentication and Authorization
- Secure login system for students and faculty
- Role-based access control (Student/Faculty)
- JWT-based authentication

### Student Features
- View available courses
- Enroll in courses
- View personal grades
- Access student dashboard
- View academic history

### Faculty Features
- Manage course grades
- View enrolled students
- Access faculty dashboard
- Grade submission system

### Course Management
- Course listing and details
- Course capacity management
- Prerequisites handling
- Academic term organization

### Grade Management
- Grade submission by faculty
- Grade viewing for students
- Term-wise grade organization
- Academic performance tracking

## Technology Stack

- **Backend**: Spring Boot Microservices
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Database**: PostgreSQL
- **Container**: Docker
- **API Gateway**: Spring Cloud Gateway
- **Authentication**: JWT (JSON Web Tokens)

## System Architecture

The system consists of the following microservices:

1. **Frontend Service** (Port: 8085)
   - Handles UI rendering and user interaction

2. **API Gateway** (Port: 8080)
   - Routes requests to appropriate services
   - Handles authentication verification

3. **Auth Service** (Port: 8081)
   - Manages user authentication
   - Handles JWT token generation and validation

4. **Course Service** (Port: 8082)
   - Manages course-related operations
   - Handles course listings and details

5. **Enrollment Service** (Port: 8083)
   - Processes student enrollments
   - Manages enrollment status

6. **Grade Service** (Port: 8084)
   - Handles grade submissions
   - Manages grade records

## Prerequisites

- Docker and Docker Compose
- Java 17 or higher
- PostgreSQL (handled by Docker)
- Maven (for local development)

## Getting Started

### Environment Setup

1. Clone the repository:
\`\`\`bash
git clone [repository-url]
cd enrollment-system
\`\`\`

2. Create a .env file in the root directory:
\`\`\`bash
JWT_SECRET=your_jwt_secret_key_here
\`\`\`

### Running the Application

1. Start all services using Docker Compose:
\`\`\`bash
docker-compose up -d
\`\`\`

2. The services will be available at:
   - Frontend: http://localhost:8085
   - API Gateway: http://localhost:8080
   - Auth Service: http://localhost:8081
   - Course Service: http://localhost:8082
   - Enrollment Service: http://localhost:8083
   - Grade Service: http://localhost:8084

### Database

The system uses PostgreSQL with multiple schemas:
- auth: Authentication related tables
- course: Course management tables
- enrollment: Enrollment records
- grade: Grade management tables

The database is automatically initialized when running with Docker Compose.