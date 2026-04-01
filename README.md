# Spring Boot Blog API

A production-ready **RESTful API** for a blog application built with **Spring Boot**, **PostgreSQL**, and **JWT authentication**. This API provides secure user authentication and full CRUD operations for blog posts. It’s designed with scalability, clean code, and best practices in mind, making it a great starting point for blogging platforms or Spring Boot REST API projects.

---

## Features

* **User Authentication**: Register and login with JWT token-based authentication.
* **Blog Management**: Create, read, update, and delete blog posts.
* **Health Check**: Endpoint to verify the application status.
* **Standardized API Responses**: Every response includes `status`, `message`, `data`, and `timestamp`.
* **Security**: Stateless authentication with Spring Security and JWT.
* **Error Handling**: Structured error responses for better frontend integration.

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
git clone [https://github.com/your-username/spring-boot-blog-api](https://github.com/IbrahimYemi/spring-boot-blog-api.git
cd spring-boot-blog-api
```

2. **Configure environment variables**

Create a `.env` file in the root folder or use `application.properties`/`application.yml` for your PostgreSQL database and JWT settings:

```properties
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=blog_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_password

JWT_SECRET=your_jwt_secret
```

3. **Run the application**

```bash
mvn clean install
mvn spring-boot:run
```

The API will start on **[http://localhost:8080](http://localhost:8080)** by default.

---

## API Endpoints

### Authentication

| Method | Endpoint             | Description           |
| ------ | -------------------- | --------------------- |
| POST   | `/api/auth/register` | Register a new user   |
| POST   | `/api/auth/login`    | Login and receive JWT |

### Blog Posts

| Method | Endpoint          | Description            |
| ------ | ----------------- | ---------------------- |
| GET    | `/api/posts`      | Get all blog posts     |
| GET    | `/api/posts/{id}` | Get a single blog post |
| POST   | `/api/posts`      | Create a new blog post |
| PUT    | `/api/posts/{id}` | Update a blog post     |
| DELETE | `/api/posts/{id}` | Delete a blog post     |

### Health Check

| Method | Endpoint           | Description      |
| ------ | ------------------ | ---------------- |
| GET    | `/api/auth/health` | Check API status |

---

## Response Format

All API responses are standardized with:

```json
{
  "status": "success | error",
  "message": "Descriptive message",
  "data": { ... },
  "errors": { ... } | null,
  "timestamp": "2026-04-01T15:30:00"
}
```

---

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

---

## License

This project is **MIT licensed**.
