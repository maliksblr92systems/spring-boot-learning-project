# Spring Boot Learning Project

This project is a Spring Boot implementation demonstrating multiple enterprise-level features and best practices. It serves as a learning reference for building robust Spring Boot applications.

## Project Info

- **Spring Boot Version:** 3.5.7  
- **Java Version:** 21  
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
   - Entities include `AppUserModel`, `Category`, `Product`, and `Customer`.
   - One-to-Many and Many-to-One relationship implemented between `Category` and `Product`.
   - Follows best practices for JPA and database integration.

3. **Liquibase for Database Migrations**
   - Replaced Flyway with Liquibase for schema migrations.
   - All schema creation, including ShedLock and Spring Batch tables, is managed via Liquibase YAML changelogs.
   - Flyway configuration retained but commented out for reference.

4. **Spring Batch Example**
   - Reading customer records from CSV files and writing them to the database.
   - Demonstrates batch processing and file handling.

5. **ShedLock Integration**
   - Sample scheduled task implemented to demonstrate concurrency control across multiple instances.
   - ShedLock schema creation is managed via Liquibase.

6. **MapStruct Integration**
   - Mapped all DTOs to Entities and vice versa.
   - Supports complex mappings with nested child DTOs (`CategoryDto` ↔ `ProductDto`).

7. **Spring AOP Logging**
   - Method-level logging using Aspect-Oriented Programming.
   - Logs entry, exit, and execution time for services and controllers.

8. **Global Exception Handling**
   - Using `@RestControllerAdvice` for handling exceptions globally.
   - Custom `ApiException` class provides structured error responses.

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
| Liquibase Core | 4.25.0 | Database schema migrations |
| JJWT API | 0.12.5 | JWT token handling |
| JJWT Impl | 0.12.5 | JWT implementation |
| JJWT Jackson | 0.12.5 | JWT JSON support |
| MapStruct | 1.5.5.Final | Entity ↔ DTO mapping |
| ShedLock Spring & JDBC | 5.8.0 | Distributed locking for scheduled tasks |
| Lombok | latest | Boilerplate reduction (annotations) |
| Spring Boot Starter Test | 3.5.7 | Unit & integration testing |
| JUnit Platform Launcher | latest | Test runtime |

---

## Getting Started

1. **Clone the repository:**

   ```bash
   git clone git@github.com:maliksblr92systems/spring-boot-learning-project.git
