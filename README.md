# Fruit APIs – CRUD Exercises (Levels 1, 2 and 3)

## 📄 Description – Exercise Overview

This repository contains three independent but progressive REST API projects developed with Spring Boot, as part of the ItAcademy backend track. Each level focuses on a different persistence technology and architectural challenge, following professional best practices such as clean architecture, DTO usage, validation, global exception handling, testing, and Dockerization.

### Level 1 – CRUD with H2
A REST API to manage fruits using an in-memory H2 database.  
This level focuses on:
- Basic CRUD operations
- Clean MVC architecture
- DTOs and Bean Validation
- Global exception handling
- Unit and controller tests
- Java 21 and Spring Boot 3

### Level 2 – CRUD with MySQL (Providers & Fruits)
An extension of Level 1 where fruit providers are introduced and persisted in MySQL.  
This level focuses on:
- Entity relationships (Fruit ↔ Provider)
- @ManyToOne associations with JPA
- Business constraints and conflict handling
- Docker & Docker Compose (MySQL + API)
- Environment-based configuration
- Advanced testing (service + controller)

### Level 3 – CRUD with MongoDB (Orders)
A completely independent project that manages fruit orders using MongoDB as a document database.  
This level focuses on:
- MongoDB documents and embedded documents
- Order and OrderItem modeling
- Validation of complex request payloads
- Date-based business rules
- MongoDB + Docker integration

⚠️ **Important**:  
Level 3 is **not related** to Level 2 in terms of domain or persistence. It is a separate exercise designed to practice MongoDB and document-based modeling.

---

## 💻 Technologies Used

- Java 21 (LTS)
- Spring Boot 3.x
- Spring Web
- Spring Data JPA (Levels 1 & 2)
- Spring Data MongoDB (Level 3)
- H2 Database (Level 1)
- MySQL 8 (Level 2)
- MongoDB 7 (Level 3)
- Hibernate
- Bean Validation (Jakarta Validation)
- JUnit 5
- Mockito
- MockMvc
- Docker & Docker Compose
- Maven

---

## 📋 Requirements

- Java 21+
- Maven 3.9+
- Docker & Docker Compose
- Git
- Optional: Postman or similar REST client

---

## 🛠️ Installation

1. Clone the repository:
```bash
git clone https://github.com/your-username/fruit-apis.git
cd fruit-apis
```

2. Build the project:
```bash
mvn clean package
```

---

## ▶️ Execution

### Local execution (without Docker)

#### Level 1 (H2)
```bash
mvn spring-boot:run
```
- Application runs on `http://localhost:8080`
- H2 Console: `/h2-console`

#### Level 2 (MySQL)
Make sure MySQL is running and configured, then:
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

#### Level 3 (MongoDB)
Make sure MongoDB is running, then:
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

---

## 🌐 Deployment (Docker)

Each level includes:
- Multi-stage Dockerfile
- Docker Compose configuration
- Environment variables for DB connection

### Example:
```bash
docker compose up --build
```

---

## 🔌 Main Endpoints Overview

### Level 1 – Fruits (H2)
- `POST /fruits`
- `GET /fruits`
- `GET /fruits/{id}`
- `PUT /fruits/{id}`
- `DELETE /fruits/{id}`

### Level 2 – Providers & Fruits (MySQL)
- `POST /providers`
- `GET /providers`
- `PUT /providers/{id}`
- `DELETE /providers/{id}`
- `GET /fruits?providerId={id}`

### Level 3 – Orders (MongoDB)
- `POST /orders`
- `GET /orders`
- `GET /orders/{id}`
- `PUT /orders/{id}`
- `DELETE /orders/{id}`

---

## 🧪 Testing Strategy

- Service layer tests with Mockito
- Controller tests with MockMvc
- Validation and error-case coverage
- TDD-oriented workflow (red → green → refactor)

---

## 🤝 Contributions

This repository is intended as an educational project.  
If you wish to contribute:
- Follow clean architecture principles
- Add tests for any new functionality
- Keep consistency with existing conventions


