# 04 - TDD with JUnit 5 and Mockito

## Objectives

- Practice Test-Driven Development (TDD) by writing tests in strict
  Arrange-Act-Assert (AAA) style.
- Use JUnit 5 core features: `@Test`, `@BeforeEach`, `@DisplayName`,
  `@ParameterizedTest` with `@CsvSource`, and `assertThrows` for exception
  testing.
- Use Mockito to isolate a service under test from its collaborators:
  `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks`,
  `when(...).thenReturn(...)`, `verify(...)`, and `ArgumentCaptor`.
- Group related tests with a JUnit 5 `@Suite` (`junit-platform-suite`).

## Module contents

| Class | Purpose |
|---|---|
| `Calculator` | `add`, `subtract`, `multiply`, `divide` (throws `ArithmeticException` on divide-by-zero) |
| `CalculatorTest` | AAA-style tests, including a `@ParameterizedTest` + `@CsvSource` for `add`, and an `assertThrows` test for divide-by-zero |
| `BankAccount` | `deposit`, `withdraw` (throws `InsufficientFundsException`), `getBalance` |
| `InsufficientFundsException` | Custom unchecked exception used by `BankAccount` |
| `BankAccountTest` | Covers deposit, successful withdraw, and the insufficient-funds exception path |
| `User` | Simple POJO: `id`, `name`, `email` |
| `UserRepository` | `findById`, `findByEmail`, `save`, `deleteById` |
| `UserService` | `registerUser` (throws `IllegalArgumentException` on duplicate email), `getUser` (throws `NoSuchElementException` when missing) |
| `UserServiceTest` | Mockito-based tests: stubbing, `verify`, `ArgumentCaptor`, and the not-found exception path |
| `AllTestsSuite` | `@Suite` + `@SelectPackages("com.dn5.week2.tdd")` to run every test in the package together |

## Prerequisites

- JDK 17
- Maven 3.8+

## Build / run / test commands

Run from inside this module's folder
(`Week-2-Languages-and-Spring-Core/04-TDD-JUnit5-Mockito`):

```bash
# Compile and run the full test suite (runs CalculatorTest, BankAccountTest,
# UserServiceTest individually - this is the primary command for this module)
mvn test

# Compile only
mvn compile

# Full build (compile + test + package into target/tdd-junit5-mockito-1.0.0.jar)
mvn package
```

### Running the `AllTestsSuite` aggregate suite

`AllTestsSuite` demonstrates the JUnit 5 `@Suite` + `@SelectPackages` feature.
Maven Surefire's JUnit Platform provider has a known integration limitation
with `junit-platform-suite-engine` (the provider's own class filters leak
into the suite's nested discovery request and can report "Tests run: 0"),
so the suite is best executed with the standalone JUnit Platform Console
Launcher, or run directly from an IDE (IntelliJ/Eclipse run each fully
support `@Suite`):

```bash
mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt test-compile
java -jar junit-platform-console-standalone-1.10.2.jar execute \
  --classpath=target/classes --classpath=target/test-classes --classpath="$(cat cp.txt)" \
  --select-class=com.dn5.week2.tdd.AllTestsSuite --details=tree
```

(download the console launcher once with:
`mvn dependency:get -Dartifact=org.junit.platform:junit-platform-console-standalone:1.10.2`,
then reference the jar from your local `~/.m2/repository` path.) All 16
tests across the three test classes pass when run this way.
