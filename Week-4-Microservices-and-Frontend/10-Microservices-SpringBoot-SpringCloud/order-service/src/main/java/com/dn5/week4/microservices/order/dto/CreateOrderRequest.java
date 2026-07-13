package com.dn5.week4.microservices.order.dto;

/**
 * Request body for POST /api/orders.
 */
public record CreateOrderRequest(Long productId, int quantity) {
}
