package com.dn5.week1.designpatterns.solid.ocp;

/** Regular customers get a flat 5% discount. */
public class RegularCustomerDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.95;
    }
}
