package com.dn5.week2.logging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Exercises {@link OrderService} so that logging statements run, and verifies
 * the returned {@link Order} has the expected state.
 */
class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @Test
    void placeOrder_returnsOrderWithPlacedStatus() {
        // Arrange
        Order order = Order.builder()
                .id(1L)
                .customerName("Alice")
                .amount(99.95)
                .status("NEW")
                .build();

        // Act
        Order result = orderService.placeOrder(order);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Alice", result.getCustomerName());
        assertEquals(99.95, result.getAmount(), 0.0001);
        assertEquals("PLACED", result.getStatus());
    }

    @Test
    void cancelOrder_doesNotThrow() {
        // Arrange, Act & Assert - only exercises the WARN log path
        orderService.cancelOrder(2L);
    }

    @Test
    void processOrderWithSimulatedFailure_isHandledAndDoesNotPropagate() {
        // Arrange, Act & Assert - the simulated RuntimeException is caught
        // internally and logged at ERROR level, so no exception escapes here.
        orderService.processOrderWithSimulatedFailure(3L);
    }
}
