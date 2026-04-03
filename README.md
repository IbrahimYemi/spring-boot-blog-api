# Spring Boot Blog API

A production-grade **RESTful API** built with **Spring Boot**, designed with a strong emphasis on **clean architecture, SOLID principles, and scalability**. The system implements secure **JWT-based authentication with refresh tokens** and robust **role-based access control (USER/ADMIN)**, ensuring proper separation of concerns between security and business logic. It provides full **blog CRUD functionality** with strict ownership enforcement and admin override capabilities, alongside comprehensive **user profile management** (profile updates, password changes, and avatar uploads). The file upload system is architected using the **Strategy pattern**, allowing seamless extension from local storage to cloud providers (e.g., S3, Cloudinary) without modifying core logic. With DTO abstraction, global exception handling, request validation, stateless security, pagination, and standardized API responses, this project serves as a solid and extensible foundation for real-world applications.

---

## Features

* **Authentication & Authorization**

    * JWT-based authentication with refresh tokens
    * Role-based access control (USER / ADMIN)
    * Stateless security with Spring Security

* **User Management**

    * Retrieve authenticated user profile
    * Update profile details
    * Change password securely
    * Upload avatar (image validation included)
    * File storage designed using **Strategy Pattern** (extensible to S3, Cloudinary, etc.)

* **Blog Management**

    * Create, read, update, and delete blog posts
    * Ownership enforcement (users manage their own posts)
    * Admin override capabilities

* **Architecture & Design**

    * Layered architecture (Controller → Service → Repository)
    * DTO-based request/response separation
    * Global exception handling
    * Input validation with `@Valid`
    * Consistent API response structure

* **System Utilities**

    * Pagination support
    * Health check endpoint

---

## Tech Stack

* **Backend**: Spring Boot
* **Database**: PostgreSQL
* **Authentication**: JWT (JSON Web Tokens)
* **Build Tool**: Maven
* **Testing**: JUnit, Spring Boot Test

---

## Getting Started

### Prerequisites

* Java 25 or higher
* Maven
* PostgreSQL
* Git

---

### Installation

1. **Clone the repository**

```bash
git clone https://github.com/IbrahimYemi/spring-boot-blog-api.git
cd spring-boot-blog-api
```

---

2. **Configure environment variables**

Create a `.env` file or configure via `application.properties` / `application.yml`:

```properties
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=blog_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_password

JWT_SECRET=your_jwt_secret
```

---

3. **Run the application**

```bash
mvn clean install
mvn spring-boot:run
```

API runs at:

```
http://localhost:8080
```

---

## API Endpoints

### Authentication

| Method | Endpoint             | Description                  |
| ------ | -------------------- | ---------------------------- |
| POST   | `/api/auth/register` | Register a new user          |
| POST   | `/api/auth/login`    | Login and receive JWT tokens |

---

### User Profile

| Method | Endpoint                 | Description                  |
| ------ | ------------------------ | ---------------------------- |
| GET    | `/api/users/me`          | Get current user profile     |
| PUT    | `/api/users/me`          | Update profile details       |
| PUT    | `/api/users/me/password` | Change user password         |
| PUT    | `/api/users/me/avatar`   | Upload/update avatar (image) |

---

### Blog Posts

| Method | Endpoint          | Description            |
| ------ | ----------------- | ---------------------- |
| GET    | `/api/posts`      | Get all blog posts     |
| GET    | `/api/posts/{id}` | Get a single blog post |
| POST   | `/api/posts`      | Create a new blog post |
| PUT    | `/api/posts/{id}` | Update a blog post     |
| DELETE | `/api/posts/{id}` | Delete a blog post     |

---

### Health Check

| Method | Endpoint           | Description      |
| ------ | ------------------ | ---------------- |
| GET    | `/api/auth/health` | Check API status |

---

## File Upload Design (Key Highlight)

The file upload system is implemented using the **Strategy Pattern**, allowing dynamic selection of storage providers via configuration:

```yaml
file:
  upload-channel: local
```

Supported (extensible) channels:

* Local storage ✅
* AWS S3 (plug-and-play)
* Cloudinary (plug-and-play)

This design ensures:

* **Open/Closed Principle compliance**
* Easy extensibility without modifying core logic
* Clean separation of concerns

---

## Response Format

All API responses follow a consistent structure:

```json
{
  "status": "success | error",
  "message": "Descriptive message",
  "data": { },
  "errors": null,
  "timestamp": "2026-04-01T15:30:00"
}
```

---

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

---

## License

This project is **MIT licensed**.
