package com.dn5.week4.microservices.order.dto;

/**
 * Shape returned by product-service's GET /api/products/{id}, mirrored here
 * so order-service does not need a hard compile-time dependency on the
 * product-service module.
 */
public record ProductDto(Long id, String name, double price) {
}
