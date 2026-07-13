package com.dn5.week1.designpatterns.solid.ocp;

/** VIP customers get a flat 20% discount. */
public class VipCustomerDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        return amount * 0.80;
    }
}
