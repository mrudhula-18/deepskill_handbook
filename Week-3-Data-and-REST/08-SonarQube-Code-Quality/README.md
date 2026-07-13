# 08 - SonarQube Code Quality

## Objectives

- Understand what SonarQube is and what it measures.
- See real, concrete static-analysis findings (duplication, complexity,
  code smells, bugs) in a deliberately messy class.
- Refactor that class into clean, testable code and confirm the
  improvement with test coverage.
- Run the Sonar Maven Scanner against a local SonarQube server and
  understand the Quality Gate concept.

## What is SonarQube?

SonarQube is a static-analysis platform that scans source code (without
running it) to detect **bugs**, **vulnerabilities**, and **code smells**,
and to compute metrics such as **cyclomatic complexity**, **duplicated
lines/blocks**, and **test coverage** (when a coverage report, e.g. from
JaCoCo, is supplied). Results are published to a SonarQube server as a
dashboard, and a **Quality Gate** - a set of pass/fail thresholds on those
metrics - determines whether a build is "clean enough" to ship.

## `before/` vs `after/`: findings and fixes

| Smell in `before.MessyOrderProcessor`                                    | SonarQube finding                                   | Fix in `after.OrderProcessor`                                                              |
|----------------------------------------------------------------------------|------------------------------------------------------|----------------------------------------------------------------------------------------------|
| Validation logic copy-pasted 3 times (in `processOrder` twice, plus `isValidOrderAgain`) | High duplicated-lines / duplicated-blocks %          | Single `validate(Order)` method, called once. Duplication drops to ~0%.                     |
| 4+ levels of nested `if`/`else` for discount rules                        | High cyclomatic complexity per method (Sonar "Cognitive Complexity" rule) | Nesting flattened to guard clauses; discount logic split across `calculateDiscount`, `calculateConfirmedDiscount`, `calculateUsDiscount` (each nesting depth <= 2). |
| Magic numbers: `status == 1`, `status == 2`, `status == 3`                | "Replace this magic number with a named constant" code smell | Replaced with the `OrderStatus` enum (`CONFIRMED`, `PENDING_REVIEW`, `CANCELLED`, `UNKNOWN`) and named `double` constants for rates/thresholds. |
| Empty `catch (Exception e) { }` block                                     | Bug/vulnerability: "Handle this exception or don't catch it" | Persistence failures are wrapped and rethrown as a specific `OrderPersistenceException`, so failures are never silently swallowed. |
| One `processOrder` method > 60 lines doing validation + discount math + logging + persistence | Code smell: "Refactor this method to reduce its Cognitive Complexity" / long method | Split into `processOrder`, `validate`, `calculateDiscount`, `calculateConfirmedDiscount`, `calculateUsDiscount`, `persist` - each under ~20 lines with a single responsibility. |
| No unit tests exercising the logic                                        | 0% coverage reported by Sonar                        | `OrderProcessorTest` covers the standard, bulk, low-value, international, pending-review, cancelled, and every validation-failure path. |

Both files compile; `before/MessyOrderProcessor` intentionally remains in
the source tree (not deleted) so the Sonar scan can show the contrast
between the two side by side.

## Project layout

```
src/main/java/com/dn5/week3/sonar/
  before/MessyOrderProcessor.java   - deliberately poor-quality code
  after/OrderProcessor.java          - refactored, clean version
src/test/java/com/dn5/week3/sonar/
  after/OrderProcessorTest.java     - JUnit 5 tests for OrderProcessor
```

## Build / run / test commands

Run all commands from this module's directory
(`Week-3-Data-and-REST/08-SonarQube-Code-Quality`):

```bash
# Compile
mvn clean compile

# Run the test suite (also produces a JaCoCo coverage report under target/site/jacoco)
mvn test

# Full build
mvn clean verify
```

### Running the SonarQube analysis

1. Start a local SonarQube server (Community Edition) via Docker:

   ```bash
   docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
   ```

   Wait for it to come up, then log in at `http://localhost:9000`
   (default credentials `admin` / `admin`, you will be asked to change the
   password) and generate a user token under
   **My Account > Security > Generate Tokens**.

2. Run the scanner from this module's directory:

   ```bash
   mvn clean verify sonar:sonar -Dsonar.login=<your-generated-token>
   ```

   This compiles, runs tests (producing JaCoCo coverage), and then
   uploads the analysis to `sonar.host.url` (`http://localhost:9000`)
   under the project key `dn5-week3-sonar-demo`, both configured in
   `pom.xml`.

3. Open the project dashboard in SonarQube to see duplication %,
   cyclomatic/cognitive complexity, code smells, bugs, and coverage,
   broken down per file - contrast `before/MessyOrderProcessor.java`
   against `after/OrderProcessor.java`.

## Quality Gate: pass/fail

A **Quality Gate** is a named set of conditions (e.g. "coverage on new
code >= 80%", "0 new bugs", "duplicated lines < 3%", "maintainability
rating A") evaluated after each analysis. If every condition is
satisfied the gate **passes** (green); if any condition fails the gate
**fails** (red), which CI pipelines commonly use to block a merge or
release. The default gate shipped with SonarQube is "Sonar way". In
this module, analyzing only `after/OrderProcessor.java` (with its test
suite) should comfortably pass the default gate, while analyzing
`before/MessyOrderProcessor.java` alone highlights the duplication,
complexity, and swallowed-exception issues that a strict gate would
reject.
