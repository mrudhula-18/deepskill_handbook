package com.dn5.week3.sonar.after;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.dn5.week3.sonar.after.OrderProcessor.InvalidOrderException;
import com.dn5.week3.sonar.after.OrderProcessor.Order;
import com.dn5.week3.sonar.after.OrderProcessor.OrderStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderProcessorTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    @DisplayName("US confirmed order over threshold, non-bulk, gets standard 10% discount")
    void confirmedUsOrder_standardDiscount() {
        Order order = new Order("Alice", "alice@example.com", 5, 30.0, OrderStatus.CONFIRMED, "US");

        double total = processor.processOrder(order);

        // 5 * 30 = 150, > 100 threshold, itemCount 5 <= 10 -> 10% discount
        assertEquals(135.0, total, 0.001);
    }

    @Test
    @DisplayName("US confirmed order over threshold, bulk (>10 items), gets 20% discount")
    void confirmedUsOrder_bulkDiscount() {
        Order order = new Order("Bob", "bob@example.com", 11, 20.0, OrderStatus.CONFIRMED, "US");

        double total = processor.processOrder(order);

        // 11 * 20 = 220, > 100 threshold, itemCount 11 > 10 -> 20% discount
        assertEquals(176.0, total, 0.001);
    }

    @Test
    @DisplayName("US confirmed order at or below threshold gets 5% discount")
    void confirmedUsOrder_lowValueDiscount() {
        Order order = new Order("Carol", "carol@example.com", 2, 10.0, OrderStatus.CONFIRMED, "US");

        double total = processor.processOrder(order);

        // 2 * 10 = 20, <= 100 threshold -> 5% discount
        assertEquals(19.0, total, 0.001);
    }

    @Test
    @DisplayName("International confirmed order over threshold gets 8% discount")
    void confirmedInternationalOrder_highValueDiscount() {
        Order order = new Order("Dave", "dave@example.com", 3, 50.0, OrderStatus.CONFIRMED, "DE");

        double total = processor.processOrder(order);

        // 3 * 50 = 150, > 100 threshold -> 8% discount
        assertEquals(138.0, total, 0.001);
    }

    @Test
    @DisplayName("International confirmed order at or below threshold gets no discount")
    void confirmedInternationalOrder_noDiscount() {
        Order order = new Order("Eve", "eve@example.com", 1, 50.0, OrderStatus.CONFIRMED, "DE");

        double total = processor.processOrder(order);

        assertEquals(50.0, total, 0.001);
    }

    @Test
    @DisplayName("Pending-review order gets a flat 2% discount")
    void pendingReviewOrder_flatDiscount() {
        Order order = new Order("Frank", "frank@example.com", 4, 25.0, OrderStatus.PENDING_REVIEW, "US");

        double total = processor.processOrder(order);

        // 4 * 25 = 100, 2% discount
        assertEquals(98.0, total, 0.001);
    }

    @Test
    @DisplayName("Cancelled order gets no discount and logs the cancellation")
    void cancelledOrder_noDiscount() {
        Order order = new Order("Grace", "grace@example.com", 2, 15.0, OrderStatus.CANCELLED, "US");

        double total = processor.processOrder(order);

        assertEquals(30.0, total, 0.001);
        assertTrue(processor.getLog().stream().anyMatch(entry -> entry.contains("cancelled")));
    }

    @ParameterizedTest
    @DisplayName("Blank or null customer name is rejected")
    @CsvSource({"'', a@b.com, 1", "'   ', a@b.com, 1"})
    void blankCustomerName_throwsInvalidOrderException(String name, String email, int itemCount) {
        Order order = new Order(name, email, itemCount, 10.0, OrderStatus.CONFIRMED, "US");

        assertThrows(InvalidOrderException.class, () -> processor.processOrder(order));
    }

    @Test
    @DisplayName("Email without '@' is rejected")
    void invalidEmail_throwsInvalidOrderException() {
        Order order = new Order("Heidi", "not-an-email", 1, 10.0, OrderStatus.CONFIRMED, "US");

        assertThrows(InvalidOrderException.class, () -> processor.processOrder(order));
    }

    @Test
    @DisplayName("Zero or negative item count is rejected")
    void nonPositiveItemCount_throwsInvalidOrderException() {
        Order order = new Order("Ivan", "ivan@example.com", 0, 10.0, OrderStatus.CONFIRMED, "US");

        assertThrows(InvalidOrderException.class, () -> processor.processOrder(order));
    }

    @Test
    @DisplayName("Successful order is recorded in the processing log")
    void successfulOrder_isLogged() {
        Order order = new Order("Judy", "judy@example.com", 1, 10.0, OrderStatus.CONFIRMED, "US");

        processor.processOrder(order);

        assertTrue(processor.getLog().stream().anyMatch(entry -> entry.contains("Judy")));
    }
}
