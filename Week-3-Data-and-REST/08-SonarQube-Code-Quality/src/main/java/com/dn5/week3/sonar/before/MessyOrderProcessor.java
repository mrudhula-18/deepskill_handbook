package com.dn5.week3.sonar.before;

import java.util.ArrayList;
import java.util.List;

/**
 * INTENTIONALLY LOW-QUALITY CODE.
 *
 * This class exists purely as a "before" sample to demonstrate real
 * SonarQube findings. Do not use this style in production code - see
 * {@code after.OrderProcessor} for the refactored version.
 *
 * Smells deliberately baked in here:
 *   1. Copy-pasted validation logic, repeated three times (duplication).
 *   2. Deeply nested if/else chains (4+ levels of nesting).
 *   3. Magic numbers instead of named constants (e.g. "status == 3").
 *   4. An empty catch block that silently swallows exceptions.
 *   5. A single method that is far too long and does too many unrelated
 *      things (validation + discount math + logging + persistence stub).
 */
public class MessyOrderProcessor {

    private List<String> log = new ArrayList<>();

    // Smell: one giant method (>60 lines) doing validation, discount
    // calculation, status handling and "persistence" all at once.
    public double processOrder(String customerName, String customerEmail, int itemCount,
                                double unitPrice, int status, String country) {

        // --- duplicated validation block #1 (customer order) ---
        if (customerName == null || customerName.trim().length() == 0) {
            log.add("Invalid customer name");
            return -1;
        }
        if (customerEmail == null || customerEmail.indexOf("@") < 0) {
            log.add("Invalid customer email");
            return -1;
        }
        if (itemCount <= 0) {
            log.add("Invalid item count");
            return -1;
        }

        double total = itemCount * unitPrice;
        double discount = 0.0;

        // Smell: deeply nested conditionals (4+ levels)
        if (status == 1) {
            if (country != null) {
                if (country.equals("US")) {
                    if (total > 100) {
                        if (itemCount > 10) {
                            discount = total * 0.2;
                        } else {
                            discount = total * 0.1;
                        }
                    } else {
                        discount = total * 0.05;
                    }
                } else {
                    if (total > 100) {
                        discount = total * 0.08;
                    } else {
                        discount = 0.0;
                    }
                }
            }
        } else if (status == 2) {
            discount = total * 0.02;
        } else if (status == 3) {
            // Smell: magic number "3" means "cancelled" but nothing says so.
            log.add("Order cancelled, no discount applies");
            discount = 0.0;
        } else {
            discount = 0.0;
        }

        total = total - discount;

        // --- duplicated validation block #2 (same checks again, "just in case") ---
        if (customerName == null || customerName.trim().length() == 0) {
            log.add("Re-check: invalid customer name");
            return -1;
        }
        if (customerEmail == null || customerEmail.indexOf("@") < 0) {
            log.add("Re-check: invalid customer email");
            return -1;
        }
        if (itemCount <= 0) {
            log.add("Re-check: invalid item count");
            return -1;
        }

        try {
            persistOrderStub(customerName, total);
        } catch (Exception e) {
            // Smell: empty catch block, error is silently swallowed.
        }

        log.add("Order processed for " + customerName + " total=" + total);
        return total;
    }

    // --- duplicated validation block #3, extracted but still copy-pasted logic ---
    public boolean isValidOrderAgain(String customerName, String customerEmail, int itemCount) {
        if (customerName == null || customerName.trim().length() == 0) {
            return false;
        }
        if (customerEmail == null || customerEmail.indexOf("@") < 0) {
            return false;
        }
        if (itemCount <= 0) {
            return false;
        }
        return true;
    }

    private void persistOrderStub(String customerName, double total) {
        // Pretend to save to a database.
        log.add("Persisted order for " + customerName + " with total " + total);
    }

    public List<String> getLog() {
        return log;
    }
}
