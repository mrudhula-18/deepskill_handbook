package com.dn5.week4.microservices.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Hardcoded, in-memory product catalog. In a real system this would be
 * backed by a repository/database; for this deep-skilling module the goal
 * is to demonstrate service registration, REST endpoints, and being called
 * through Feign + the gateway, not persistence.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final List<Product> PRODUCTS = List.of(
            new Product(1L, "Wireless Mouse", 19.99),
            new Product(2L, "Mechanical Keyboard", 79.50),
            new Product(3L, "27-inch Monitor", 249.00),
            new Product(4L, "USB-C Docking Station", 129.95)
    );

    @GetMapping
    public List<Product> getAllProducts() {
        return PRODUCTS;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return PRODUCTS.stream()
                .filter(product -> product.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found with id " + id));
    }
}
