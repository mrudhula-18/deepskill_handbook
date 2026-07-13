# 10 - Microservices with Spring Boot & Spring Cloud

## Objectives

- Stand up a Netflix Eureka service registry and register services with it.
- Build two independent business microservices (`product-service`,
  `order-service`) that discover each other through Eureka instead of
  hardcoded URLs.
- Call one service from another declaratively with **OpenFeign**.
- Protect an inter-service call with a **Resilience4j circuit breaker** and
  a fallback, so `order-service` degrades gracefully if `product-service`
  is unavailable.
- Front every service with a single entry point using **Spring Cloud
  Gateway**, routing by service discovery instead of static
  `host:port` mappings.

Versions used (must stay paired): JDK 17, Spring Boot **3.2.5**, Spring
Cloud **2023.0.1** (the "release train" compatible with Boot 3.2.x).

## Architecture

```
                                   ┌─────────────────────┐
                                   │   eureka-server      │
                                   │   localhost:8761      │
                                   │  (service registry)   │
                                   └───────────▲───────────┘
                                               │ register / discover
                     ┌─────────────────────────┼─────────────────────────┐
                     │                         │                         │
                     │                         │                         │
        ┌────────────┴───────────┐   ┌─────────┴──────────────┐          │
        │   product-service       │   │   order-service         │          │
        │   localhost:8081         │◄──┤   localhost:8082         │          │
        │   GET /api/products      │Feign│  POST /api/orders       │          │
        │   GET /api/products/{id} │+CB  │  GET  /api/orders/{id}  │          │
        └────────────▲────────────┘   └─────────▲───────────────┘          │
                     │                          │                          │
                     │        lb://product-service / lb://order-service    │
                     │                          │                          │
                     └───────────────┬──────────┘                          │
                                     │                                     │
                          ┌──────────┴───────────┐                         │
                          │     api-gateway        │◄────────────────────────┘
                          │     localhost:8080       │   registers with Eureka too
                          │  /api/products/**  → product-service
                          │  /api/orders/**    → order-service
                          └───────────▲───────────┘
                                      │
                                   client / curl
```

`order-service` calls `product-service` through a Feign client
(`ProductClient`, resolved via Eureka + a client-side load balancer, not a
hardcoded URL). That call is wrapped in a Resilience4j circuit breaker
(`productService`); if `product-service` is down or erroring past the
configured failure-rate threshold, calls short-circuit to a fallback method
that returns a placeholder product instead of failing the order.

## Startup order

Services must be started in this order so registration/discovery works:

1. **eureka-server** (port 8761) - the registry must be up first.
2. **product-service** (port 8081) and **order-service** (port 8082) - can
   start in either order/parallel, but give them ~10-30s after starting to
   show up in Eureka before calling endpoints that depend on discovery.
3. **api-gateway** (port 8080) - starts last since its routes resolve
   service ids (`lb://product-service`, `lb://order-service`) through
   Eureka.

You can confirm registration by opening the Eureka dashboard at
`http://localhost:8761` - all three services should be listed as `UP`.

## Build & run

Each project is a fully independent Maven project (its own `pom.xml`,
`spring-boot-starter-parent` 3.2.5). Build/run them from inside each
folder, in 4 separate terminals, in the order above:

```bash
# terminal 1
cd 10-Microservices-SpringBoot-SpringCloud/eureka-server
mvn clean package
mvn spring-boot:run

# terminal 2
cd 10-Microservices-SpringBoot-SpringCloud/product-service
mvn clean package
mvn spring-boot:run

# terminal 3
cd 10-Microservices-SpringBoot-SpringCloud/order-service
mvn clean package
mvn spring-boot:run

# terminal 4
cd 10-Microservices-SpringBoot-SpringCloud/api-gateway
mvn clean package
mvn spring-boot:run
```

Or run the packaged jars directly after `mvn clean package`:

```bash
java -jar target/eureka-server-1.0.0.jar
java -jar target/product-service-1.0.0.jar
java -jar target/order-service-1.0.0.jar
java -jar target/api-gateway-1.0.0.jar
```

## Testing through the gateway

Once all four are up:

```bash
# List the product catalog (routed to product-service)
curl http://localhost:8080/api/products

# Fetch a single product
curl http://localhost:8080/api/products/1

# 404 for a product that doesn't exist
curl -i http://localhost:8080/api/products/999

# Place an order (order-service calls product-service via Feign to enrich it)
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":2}'

# Fetch an order by id
curl http://localhost:8080/api/orders/1
```

You can also hit each service directly (bypassing the gateway) on its own
port, e.g. `curl http://localhost:8081/api/products`, which is useful when
isolating whether a problem is in routing/discovery vs. the service itself.

### Exercising the circuit breaker

Stop `product-service` (Ctrl+C in its terminal) and then place another
order through the gateway:

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"productId":1,"quantity":1}'
```

After a handful of failed calls the `productService` circuit breaker in
`order-service` opens (per the thresholds in its `application.yml`) and the
order is still created, but with the fallback product
(`"Unknown Product (service unavailable)"`, price `0.0`) instead of a
timeout/500. You can inspect breaker state at
`http://localhost:8082/actuator/health` or
`http://localhost:8082/actuator/circuitbreakers`.
