package com.dn5.week4.microservices.product;

/**
 * Immutable product record served by the catalog endpoints.
 */
public record Product(Long id, String name, double price) {
}
