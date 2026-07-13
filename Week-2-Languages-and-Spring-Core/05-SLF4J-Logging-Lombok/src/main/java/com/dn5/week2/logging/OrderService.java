package com.dn5.week2.logging;

import lombok.extern.slf4j.Slf4j;

/**
 * Service demonstrating parameterized SLF4J logging at INFO, WARN and ERROR
 * levels via Lombok's {@code @Slf4j}.
 */
@Slf4j
public class OrderService {

    public Order placeOrder(Order order) {
        log.info("Order {} placed for {} - amount {}", order.getId(), order.getCustomerName(), order.getAmount());
        order.setStatus("PLACED");
        return order;
    }

    public void cancelOrder(Long id) {
        log.warn("Order {} is being cancelled", id);
    }

    /**
     * Simulates processing that fails, demonstrating error logging with an
     * attached exception.
     */
    public void processOrderWithSimulatedFailure(Long id) {
        try {
            simulateFailure(id);
        } catch (RuntimeException exception) {
            log.error("Failed to process order {}", id, exception);
        }
    }

    private void simulateFailure(Long id) {
        throw new RuntimeException("Simulated processing failure for order " + id);
    }
}
