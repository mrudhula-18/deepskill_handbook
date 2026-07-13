# 05 - SLF4J Logging with Lombok

## Objectives

- Configure SLF4J with Logback as the binding, using a console appender and a
  pattern that includes timestamp, level, logger name and message.
- Use Lombok annotations (`@Data`, `@Builder`, `@NoArgsConstructor`,
  `@AllArgsConstructor`, `@Slf4j`) to eliminate boilerplate.
- Practice parameterized logging (`log.info("... {} ...", arg)`) at INFO,
  WARN and ERROR levels, including logging an exception object.
- Wire the Lombok annotation processor into `maven-compiler-plugin` via
  `annotationProcessorPaths`.

## Module contents

| File | Purpose |
|---|---|
| `src/main/resources/logback.xml` | Console appender, pattern with timestamp/level/logger/message, root level INFO |
| `Order` | Lombok `@Data @Builder @NoArgsConstructor @AllArgsConstructor` with `id, customerName, amount, status` |
| `OrderService` | `@Slf4j`; `placeOrder` (INFO), `cancelOrder` (WARN), `processOrderWithSimulatedFailure` (ERROR with exception) |
| `Main` | Demo runner that calls all three logging paths so console output shows INFO/WARN/ERROR lines |
| `OrderServiceTest` | Asserts `placeOrder` returns the expected `Order` and exercises the WARN/ERROR paths |

## Prerequisites

- JDK 17
- Maven 3.8+
- An IDE or `javac` with annotation-processing support for Lombok (Maven
  handles this automatically via `annotationProcessorPaths`)

## Build / run / test commands

Run from inside this module's folder
(`Week-2-Languages-and-Spring-Core/05-SLF4J-Logging-Lombok`):

```bash
# Compile and run tests
mvn test

# Run the demo (prints INFO/WARN/ERROR log lines to the console)
mvn compile exec:java -Dexec.mainClass="com.dn5.week2.logging.Main"

# Alternative: package then run with the classpath
mvn package
java -cp target/classes;target/dependency/* com.dn5.week2.logging.Main
```

Note: `exec:java` requires the `exec-maven-plugin`; if it is not available in
your local Maven setup, build the jar with `mvn package` and run `Main`
directly from your IDE or via `java -cp target/classes:<slf4j-and-logback-jars> com.dn5.week2.logging.Main`
after resolving dependencies with `mvn dependency:build-classpath`.
