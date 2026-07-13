package com.dn5.week4.microservices.order.service;

import com.dn5.week4.microservices.order.client.ProductClient;
import com.dn5.week4.microservices.order.dto.ProductDto;
import com.dn5.week4.microservices.order.model.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final ProductClient productClient;
    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public OrderService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public Order createOrder(Long productId, int quantity) {
        ProductDto product = fetchProduct(productId);

        Order order = new Order(
                sequence.getAndIncrement(),
                productId,
                quantity,
                product.name(),
                product.price());

        orders.put(order.getId(), order);
        return order;
    }

    public Order getOrder(Long id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with id " + id);
        }
        return order;
    }

    /**
     * Calls product-service through Feign. If product-service is down, slow,
     * or returning errors above the configured failure-rate threshold, the
     * resilience4j circuit breaker "productService" (see application.yml)
     * opens and short-circuits straight to {@link #productFallback}
     * without waiting on the network call.
     */
    @CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public ProductDto fetchProduct(Long productId) {
        return productClient.getProduct(productId);
    }

    @SuppressWarnings("unused") // invoked by resilience4j via reflection
    private ProductDto productFallback(Long productId, Throwable t) {
        log.warn("product-service unavailable, falling back for productId={}: {}", productId, t.toString());
        return new ProductDto(productId, "Unknown Product (service unavailable)", 0.0);
    }
}
