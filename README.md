# Spring Boot Learning Project

This project is a Spring Boot implementation demonstrating multiple enterprise-level features and best practices. It serves as a learning reference for building robust Spring Boot applications.

## Project Info

- **Spring Boot Version:** 3.5.7  
- **Java Version:** 17  
- **Architecture:** Three-layer architecture (Controller → Service → Repository)  
- **Group:** `com.evergreen`  
- **Version:** 0.0.1-SNAPSHOT  

---

## Features

1. **Spring Security with JWT**
   - Complete JWT-based authentication and authorization.
   - APIs for login, register, and `is-authenticated`.
   - Custom `JwtFilter` and `JwtService` for validating and generating tokens.

2. **JPA Entities with PostgreSQL**
   - Entities include `AppUser`, `Category`, and `Customer`.
   - Follows best practices for JPA and database integration.

3. **Spring Batch Example**
   - Reading customer records from CSV files and writing them to the database.
   - Demonstrates batch processing and file handling.

4. **Lambda vs Anonymous Inner Classes**
   - Examples showcasing the differences and use cases.

5. **Flyway Integration**
   - Database migrations using Flyway with best practices.

6. **CRUD Operations for Categories**
   - RESTful endpoints following industry standards.
   - Pagination support is not implemented yet.

7. **Global Exception Handling**
   - Using `@ControllerAdvice` to handle exceptions globally.

8. **Custom Error Class (`ApiException`)**
   - Provides a syntactic sugar way to throw custom exceptions.

9. **DTO-Based Requests and Responses**
   - Data Transfer Objects (DTOs) used for request and response bodies.
   - Responses aligned with `ResponseEntity` standards.

---

## Main Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| Spring Boot Starter | 3.5.7 | Core Spring Boot framework |
| Spring Boot Starter Web | 3.5.7 | REST APIs |
| Spring Boot Starter Security | 3.5.7 | Security & authentication |
| Spring Boot Starter Data JPA | 3.5.7 | Database ORM |
| Spring Boot Starter Validation | 3.5.7 | Input validation |
| Spring Boot Starter Batch | 3.5.7 | Batch processing |
| PostgreSQL Driver | latest | PostgreSQL integration |
| Flyway Core | latest | Database migrations |
| Flyway PostgreSQL | latest | PostgreSQL-specific Flyway support |
| JJWT API | 0.12.5 | JWT token handling |
| JJWT Impl | 0.12.5 | JWT implementation |
| JJWT Jackson | 0.12.5 | JWT JSON support |
| Lombok | latest | Boilerplate reduction (annotations) |
| Spring Boot Starter Test | 3.5.7 | Unit & integration testing |
| JUnit Platform Launcher | latest | Test runtime |

---

## Getting Started

1. Clone the repository:

   ```bash
   git clone git@github.com:maliksblr92systems/spring-boot-learning-project.git
