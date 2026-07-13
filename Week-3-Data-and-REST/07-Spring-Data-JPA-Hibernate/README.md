# 07 - Spring Data JPA & Hibernate

## Objectives

- Map JPA entities (`Category`, `Product`) with a `@ManyToOne` relationship using Hibernate as the JPA provider.
- Add automatic auditing (`createdAt` / `updatedAt`) via a `@MappedSuperclass` (`Auditable`) and `@EnableJpaAuditing`.
- Write Spring Data repositories using:
  - Derived query methods (`findByCategory_NameIgnoreCase`, `findByPriceGreaterThan`).
  - A custom JPQL query with `@Query` / `@Param` (`search`).
  - Pagination and sorting (`Page<Product> findByCategory_Name(String, Pageable)`).
  - An interface-based projection (`ProductNameOnly`) that fetches only a subset of columns.
- Seed an H2 in-memory database with `data.sql` after Hibernate creates the schema (`ddl-auto: create-drop` + `defer-datasource-initialization: true`).
- Test the repository layer in isolation with `@DataJpaTest` and `TestEntityManager`.

## Project layout

```
src/main/java/com/dn5/week3/jpa/
  Application.java          - @SpringBootApplication + @EnableJpaAuditing
  Auditable.java             - @MappedSuperclass with createdAt/updatedAt
  Category.java              - entity
  Product.java                - entity, extends Auditable
  ProductNameOnly.java       - projection interface
  CategoryRepository.java
  ProductRepository.java
src/main/resources/
  application.yml            - H2, JPA, H2 console config
  data.sql                    - seed data (3 categories, 9 products)
src/test/java/com/dn5/week3/jpa/
  ProductRepositoryTest.java - @DataJpaTest slice test
```

## Build / run / test commands

Run all commands from this module's directory
(`Week-3-Data-and-REST/07-Spring-Data-JPA-Hibernate`):

```bash
# Compile
mvn clean compile

# Run the Spring Boot application (starts on http://localhost:8081)
mvn spring-boot:run

# Run the test suite
mvn test

# Full build (compile + test + package)
mvn clean verify
```

Once running, the H2 console is available at `http://localhost:8081/h2-console`
(JDBC URL `jdbc:h2:mem:jpadb`, user `sa`, empty password).

## Notes

- `spring.jpa.hibernate.ddl-auto=create-drop` lets Hibernate generate the
  schema from the entity mappings on startup; `spring.jpa.defer-datasource-initialization=true`
  ensures `data.sql` runs *after* that schema exists, and
  `spring.sql.init.mode=always` forces `data.sql` execution even though an
  embedded database is in use.
- `ProductRepository.findProjectedByCategory_Name` is intentionally named
  differently from `findByCategory_Name` (the paginated variant) because
  Java/Spring Data cannot disambiguate two repository methods that differ
  only in return type.
