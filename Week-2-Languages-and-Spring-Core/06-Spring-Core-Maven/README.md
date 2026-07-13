# 06 - Spring Core with Maven

## Objectives

- Configure a Spring IoC container two ways: annotation-driven
  (`@Configuration` + `@ComponentScan`) and XML-based (`beans.xml`).
- Practice dependency injection with `@Autowired` + `@Qualifier` to
  disambiguate between multiple beans implementing the same interface.
- Enable and write AspectJ-style AOP with `@EnableAspectJAutoProxy`,
  `@Aspect` and an `@Around` advice that logs method entry/exit and timing.
- Write a Spring TestContext Framework test with `SpringExtension` and
  `@ContextConfiguration` to verify a bean graph loads correctly.

## Module contents

| File | Purpose |
|---|---|
| `EngineService` | `String start()` contract |
| `PetrolEngine` / `DieselEngine` | `@Component` implementations, qualified `"petrol"` / `"diesel"` |
| `Car` | `@Component`; constructor-injected `EngineService` via `@Autowired @Qualifier("petrol")` |
| `AppConfig` | `@Configuration @ComponentScan("com.dn5.week2.springcore") @EnableAspectJAutoProxy` |
| `LoggingAspect` | `@Aspect @Component`; `@Around("execution(* com.dn5.week2.springcore.Car.*(..))")` logging entry/exit/timing |
| `src/main/resources/beans.xml` | XML config wiring an alternate `xmlCar` bean to `dieselEngine` via constructor-arg |
| `MainAnnotationDemo` | Boots `AnnotationConfigApplicationContext(AppConfig.class)`, calls `car.start()` |
| `MainXmlDemo` | Boots `ClassPathXmlApplicationContext("beans.xml")`, calls the XML-wired car's `start()` |
| `SpringContextTest` | `@ExtendWith(SpringExtension.class) @ContextConfiguration(classes = AppConfig.class)`; asserts the `Car` bean exists and starts with the petrol engine |

## Prerequisites

- JDK 17
- Maven 3.8+

## Build / run / test commands

Run from inside this module's folder
(`Week-2-Languages-and-Spring-Core/06-Spring-Core-Maven`):

```bash
# Compile and run tests (includes context-loading test)
mvn test

# Run the annotation-based demo (prints LoggingAspect entry/exit lines + result)
mvn compile exec:java -Dexec.mainClass="com.dn5.week2.springcore.MainAnnotationDemo"

# Run the XML-based demo
mvn compile exec:java -Dexec.mainClass="com.dn5.week2.springcore.MainXmlDemo"
```

If `exec-maven-plugin` is not configured in your local Maven setup, run
`mvn dependency:build-classpath -Dmdep.outputFile=cp.txt` after `mvn compile`,
then execute `java -cp target/classes;$(cat cp.txt) com.dn5.week2.springcore.MainAnnotationDemo`
(or `MainXmlDemo`) directly, or run either class from your IDE.
