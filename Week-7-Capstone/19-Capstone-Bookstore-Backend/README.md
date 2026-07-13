# Module 19 - Capstone Bookstore Backend

Spring Boot 3.2.5 / Java 17 REST API for the DN5.0 Capstone Bookstore system. This backend is the target
API for the Module 20 React frontend (`../20-Capstone-Bookstore-Frontend-React`) and is containerized and
composed together with it in Module 21 (`../21-Capstone-Deployment-CICD`).

Default base URL: `http://localhost:8080/api/v1`

## Tech Stack

- Spring Boot 3.2.5, Java 17, Maven
- `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`
- H2 in-memory database (`jdbc:h2:mem:bookstore`)
- `springdoc-openapi-starter-webmvc-ui` 2.5.0 for Swagger UI
- JUnit 5 + Mockito + MockMvc for testing

## Project Structure

```
src/main/java/com/dn5/capstone/bookstore
├── entity           Author, Category, Book (JPA entities)
├── repository       AuthorRepository, CategoryRepository, BookRepository
├── dto              BookRequestDto, BookResponseDto
├── mapper           BookMapper
├── service          BookService, service.impl.BookServiceImpl
├── controller       BookController, AuthorController, CategoryController
├── exception        ResourceNotFoundException, GlobalExceptionHandler, ApiError
└── config           OpenApiConfig
```

## Running the Application

```bash
mvn spring-boot:run
```

The app starts on port `8080`. H2 console is available at `http://localhost:8080/h2-console`
(JDBC URL `jdbc:h2:mem:bookstore`, user `sa`, empty password). Swagger UI is available at
`http://localhost:8080/swagger-ui.html`.

On startup, `data.sql` seeds 3 authors, 3 categories, and 12 books.

## Running Tests

```bash
mvn test
```

- `BookServiceImplTest` - Mockito-based unit tests for `BookServiceImpl`, mocking all repositories.
- `BookControllerIT` - `@SpringBootTest @AutoConfigureMockMvc` end-to-end tests exercising the full
  create/read/update/delete/search flow against the real H2 database seeded by `data.sql`.

## API Endpoints

### Books - `/api/v1/books`

| Method | Path                     | Description                          | Success Status |
|--------|--------------------------|---------------------------------------|-----------------|
| GET    | `/api/v1/books`          | Paginated list of books (`Pageable`) | 200             |
| GET    | `/api/v1/books/{id}`     | Get one book by id                   | 200             |
| POST   | `/api/v1/books`          | Create a book                        | 201             |
| PUT    | `/api/v1/books/{id}`     | Update a book                        | 200             |
| DELETE | `/api/v1/books/{id}`     | Delete a book                        | 204             |
| GET    | `/api/v1/books/search?keyword=` | Search by title/author/isbn   | 200             |

A missing book/author/category id returns `404` with a consistent JSON error body:

```json
{
  "timestamp": "2026-07-13T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Book not found with id: 999",
  "path": "/api/v1/books/999"
}
```

Invalid request bodies return `400` with field-level validation messages in the same shape.

### Authors - `/api/v1/authors`

| Method | Path               | Description      |
|--------|--------------------|-------------------|
| GET    | `/api/v1/authors`  | List all authors |
| POST   | `/api/v1/authors`  | Create an author |

### Categories - `/api/v1/categories`

| Method | Path                 | Description        |
|--------|----------------------|---------------------|
| GET    | `/api/v1/categories` | List all categories |
| POST   | `/api/v1/categories` | Create a category   |

## Example curl Commands

```bash
# List books (paged)
curl "http://localhost:8080/api/v1/books?page=0&size=10&sort=title,asc"

# Get one book
curl http://localhost:8080/api/v1/books/1

# Search
curl "http://localhost:8080/api/v1/books/search?keyword=martian"

# Create a book (author 1 and category 1 must already exist)
curl -X POST http://localhost:8080/api/v1/books \
  -H "Content-Type: application/json" \
  -d '{"title":"New Book","isbn":"9780000000001","price":19.99,"stockQuantity":10,"authorId":1,"categoryId":1}'

# Update a book
curl -X PUT http://localhost:8080/api/v1/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Title","isbn":"9780000000001","price":24.99,"stockQuantity":5,"authorId":1,"categoryId":1}'

# Delete a book
curl -X DELETE http://localhost:8080/api/v1/books/1

# List authors / create author
curl http://localhost:8080/api/v1/authors
curl -X POST http://localhost:8080/api/v1/authors -H "Content-Type: application/json" -d '{"name":"New Author"}'

# List categories / create category
curl http://localhost:8080/api/v1/categories
curl -X POST http://localhost:8080/api/v1/categories -H "Content-Type: application/json" -d '{"name":"New Category"}'
```

## Frontend Integration

This backend is the API target for the Module 20 React frontend. The frontend's `.env` should point
`VITE_API_BASE_URL` at `http://localhost:8080/api/v1` when running the backend locally with `mvn spring-boot:run`.
