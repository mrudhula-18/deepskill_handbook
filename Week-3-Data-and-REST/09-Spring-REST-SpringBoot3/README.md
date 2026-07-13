# 09 - Spring REST with Spring Boot 3

## Objectives

- Build a REST API (`Book` resource) on Spring Boot 3 / Spring Data JPA.
- Add request validation (`@Valid`, Bean Validation annotations) and a
  consistent JSON error contract via `@RestControllerAdvice`.
- Add HATEOAS `self` / `collection` links to responses using
  `linkTo(methodOn(...))` and `RepresentationModel`.
- Secure the API statelessly with JWT (HS256, via `io.jsonwebtoken`),
  issued from a demo `/api/auth/login` endpoint and verified by a custom
  `OncePerRequestFilter`.
- Document the API with springdoc-openapi (Swagger UI).
- Expose health information via Spring Boot Actuator.
- Test the controller layer end-to-end with `MockMvc`.

## Project layout

```
src/main/java/com/dn5/week3/rest/
  Application.java
  entity/Book.java
  repository/BookRepository.java
  dto/BookRequestDto.java, BookResponseDto.java, BookMapper.java
  dto/LoginRequestDto.java, LoginResponseDto.java
  controller/BookController.java
  controller/AuthController.java
  exception/ResourceNotFoundException.java
  exception/ErrorResponse.java
  exception/GlobalExceptionHandler.java
  security/JwtUtil.java
  security/JwtAuthFilter.java
  config/SecurityConfig.java
  config/OpenApiConfig.java
src/main/resources/application.yml
src/test/java/com/dn5/week3/rest/controller/BookControllerTest.java
```

## Build / run / test commands

Run all commands from this module's directory
(`Week-3-Data-and-REST/09-Spring-REST-SpringBoot3`):

```bash
# Compile
mvn clean compile

# Run the application (http://localhost:8080)
mvn spring-boot:run

# Run the test suite
mvn test

# Full build
mvn clean verify
```

## Trying the API manually

1. Get a JWT (demo credentials, hardcoded server-side for this exercise
   only - see the comment in `AuthController`):

   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"admin123"}'
   ```

   Response: `{"token": "<jwt>", "tokenType": "Bearer"}`

2. Call a protected endpoint with the token:

   ```bash
   curl http://localhost:8080/api/books \
     -H "Authorization: Bearer <jwt>"
   ```

3. Create a book:

   ```bash
   curl -X POST http://localhost:8080/api/books \
     -H "Authorization: Bearer <jwt>" \
     -H "Content-Type: application/json" \
     -d '{"title":"Effective Java","author":"Joshua Bloch","isbn":"978-0134685991","price":45.00}'
   ```

4. Public endpoints (no token required): `/api/auth/**`,
   `/swagger-ui/**`, `/v3/api-docs/**`, `/actuator/health`,
   `/h2-console/**`.

## API docs and consoles

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 console: `http://localhost:8080/h2-console` (JDBC URL
  `jdbc:h2:mem:restdb`, user `sa`, empty password)
- Actuator health: `http://localhost:8080/actuator/health`

## Security notes

- `SecurityConfig` runs the API statelessly (`SessionCreationPolicy.STATELESS`)
  with CSRF disabled, since there is no browser session to protect.
- `JwtAuthFilter` (a custom `OncePerRequestFilter`) reads the
  `Authorization: Bearer <token>` header on every request, validates it
  via `JwtUtil`, and - if valid - places a `UsernamePasswordAuthenticationToken`
  into the `SecurityContextHolder` so downstream `authorizeHttpRequests`
  rules see an authenticated principal. It is registered before
  `UsernamePasswordAuthenticationFilter` in the chain.
- The `admin` / `admin123` credential in `AuthController` is a demo-only
  hardcoded shortcut so this module can be exercised without a user
  database; it is clearly commented as such and must never be used in a
  real deployment.
- `BookControllerTest` disables the security filter chain for the test
  (`@AutoConfigureMockMvc(addFilters = false)`) so it can focus on
  controller/repository behaviour. The class-level Javadoc shows the
  equivalent "real" flow of first calling `/api/auth/login` and passing
  the returned bearer token on subsequent requests.
