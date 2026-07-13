# Module 01 - Design Patterns & SOLID Principles

## Objectives

- Demonstrate each SOLID principle with a "bad" (violating) example and a "good" (fixed) example.
- Implement the classic Gang-of-Four creational, structural, and behavioral design patterns with real, runnable Java.
- Implement a minimal Model-View-Controller demo.
- Prove every example works correctly with JUnit 5 tests.

## Folder layout

```
src/main/java/com/dn5/week1/designpatterns/
  Main.java                     - runs a console demo of every pattern below
  solid/srp/                    - Single Responsibility Principle (bad + fixed)
  solid/ocp/                    - Open/Closed Principle via DiscountStrategy
  solid/lsp/                    - Liskov Substitution Principle (bad/ package + fixed Shape/Rectangle/Square)
  solid/isp/                    - Interface Segregation Principle (Workable/Eatable)
  solid/dip/                    - Dependency Inversion Principle (MessageSender abstraction)
  creational/singleton/         - thread-safe ConfigurationManager (double-checked locking)
  creational/factory/           - ShapeFactory (Factory Method)
  creational/builder/           - Pizza.Builder (fluent Builder)
  structural/adapter/           - XmlToJsonAdapter (Adapter)
  structural/decorator/         - Coffee + Milk/Sugar decorators (Decorator)
  structural/proxy/             - ProxyImage (lazy-loading Proxy)
  behavioral/observer/          - StockTicker/Investor (Observer, not java.util.Observer)
  behavioral/strategy/          - PaymentContext + CreditCard/Paypal (Strategy)
  behavioral/command/           - RemoteControl + Light commands (Command)
  mvc/                          - StudentModel/View/Controller (MVC)

src/test/java/com/dn5/week1/designpatterns/
  ... mirrors the packages above, one JUnit 5 test class per pattern
```

## Build / run / test

From this folder (`01-Design-Patterns-Principles`):

```
mvn test
```

Runs the full JUnit 5 suite (surefire). To see the console demo of every pattern:

```
mvn package
java -cp target/classes com.dn5.week1.designpatterns.Main
```

(or `mvn compile exec:java -Dexec.mainClass=com.dn5.week1.designpatterns.Main` if the `exec-maven-plugin` is available on your machine; it is not declared in `pom.xml` to keep the module dependency-free besides JUnit).

## Requirements

- JDK 17
- Maven 3.6+
- JUnit 5.10.2 (`junit-jupiter`), run via `maven-surefire-plugin` 3.x
