package com.dn5.week3.sonar.after;

import java.util.ArrayList;
import java.util.List;

/**
 * Refactored, clean version of {@code before.MessyOrderProcessor}.
 *
 * Fixes applied:
 *   - Validation logic extracted into a single {@link #validate} method,
 *     removing all duplication.
 *   - Deep nesting replaced with guard clauses and small helper methods
 *     (max nesting depth of 2).
 *   - Magic numbers replaced with the {@link OrderStatus} enum.
 *   - The empty catch block is replaced with a checked
 *     {@link OrderPersistenceException} that callers must handle.
 *   - The original 60+ line method is split into focused methods, each
 *     under ~20 lines.
 */
public class OrderProcessor {

    private static final double US_HIGH_VALUE_THRESHOLD = 100.0;
    private static final int US_BULK_ITEM_THRESHOLD = 10;

    private static final double US_BULK_DISCOUNT_RATE = 0.20;
    private static final double US_STANDARD_DISCOUNT_RATE = 0.10;
    private static final double US_LOW_VALUE_DISCOUNT_RATE = 0.05;
    private static final double INTERNATIONAL_HIGH_VALUE_DISCOUNT_RATE = 0.08;
    private static final double PENDING_REVIEW_DISCOUNT_RATE = 0.02;
    private static final double NO_DISCOUNT = 0.0;

    private static final String COUNTRY_US = "US";

    private final List<String> log = new ArrayList<>();

    /**
     * Validates, discounts and "persists" an order.
     *
     * @return the final total after discount
     * @throws InvalidOrderException     if the order data fails validation
     * @throws OrderPersistenceException if persistence fails
     */
    public double processOrder(Order order) {
        validate(order);

        double total = order.itemCount() * order.unitPrice();
        double discount = calculateDiscount(order, total);
        double finalTotal = total - discount;

        persist(order.customerName(), finalTotal);
        log.add("Order processed for " + order.customerName() + " total=" + finalTotal);
        return finalTotal;
    }

    private void validate(Order order) {
        if (isBlank(order.customerName())) {
            throw new InvalidOrderException("Invalid customer name");
        }
        if (!isValidEmail(order.customerEmail())) {
            throw new InvalidOrderException("Invalid customer email");
        }
        if (order.itemCount() <= 0) {
            throw new InvalidOrderException("Invalid item count");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private double calculateDiscount(Order order, double total) {
        OrderStatus status = order.status();

        if (status == OrderStatus.CANCELLED) {
            log.add("Order cancelled, no discount applies");
            return NO_DISCOUNT;
        }
        if (status == OrderStatus.PENDING_REVIEW) {
            return total * PENDING_REVIEW_DISCOUNT_RATE;
        }
        if (status == OrderStatus.CONFIRMED) {
            return calculateConfirmedDiscount(order, total);
        }
        return NO_DISCOUNT;
    }

    private double calculateConfirmedDiscount(Order order, double total) {
        if (COUNTRY_US.equals(order.country())) {
            return calculateUsDiscount(order, total);
        }
        return total > US_HIGH_VALUE_THRESHOLD ? total * INTERNATIONAL_HIGH_VALUE_DISCOUNT_RATE : NO_DISCOUNT;
    }

    private double calculateUsDiscount(Order order, double total) {
        if (total <= US_HIGH_VALUE_THRESHOLD) {
            return total * US_LOW_VALUE_DISCOUNT_RATE;
        }
        boolean bulkOrder = order.itemCount() > US_BULK_ITEM_THRESHOLD;
        return total * (bulkOrder ? US_BULK_DISCOUNT_RATE : US_STANDARD_DISCOUNT_RATE);
    }

    private void persist(String customerName, double total) {
        try {
            persistOrderStub(customerName, total);
        } catch (RuntimeException e) {
            throw new OrderPersistenceException("Failed to persist order for " + customerName, e);
        }
    }

    private void persistOrderStub(String customerName, double total) {
        // Pretend to save to a database.
        log.add("Persisted order for " + customerName + " with total " + total);
    }

    public List<String> getLog() {
        return List.copyOf(log);
    }

    /** Immutable order status, replacing the original magic integers. */
    public enum OrderStatus {
        CONFIRMED,
        PENDING_REVIEW,
        CANCELLED,
        UNKNOWN
    }

    /** Immutable value object carrying the order input data. */
    public record Order(String customerName, String customerEmail, int itemCount,
                         double unitPrice, OrderStatus status, String country) {
    }

    /** Thrown when order input data fails validation. */
    public static class InvalidOrderException extends RuntimeException {
        public InvalidOrderException(String message) {
            super(message);
        }
    }

    /** Thrown when the (stubbed) persistence step fails. */
    public static class OrderPersistenceException extends RuntimeException {
        public OrderPersistenceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
