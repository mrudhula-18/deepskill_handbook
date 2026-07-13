package com.dn5.week1.designpatterns.solid.ocp;

/**
 * OCP: This calculator is closed for modification - it never needs to change
 * when a new discount type is introduced - but open for extension via the
 * {@link DiscountStrategy} abstraction. No if/else or switch on a type code.
 */
public class DiscountCalculator {

    private final DiscountStrategy strategy;

    public DiscountCalculator(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double computeFinalPrice(double amount) {
        return strategy.applyDiscount(amount);
    }
}
