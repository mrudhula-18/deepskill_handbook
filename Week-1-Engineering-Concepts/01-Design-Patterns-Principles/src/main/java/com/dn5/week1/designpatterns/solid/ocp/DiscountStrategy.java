package com.dn5.week1.designpatterns.solid.ocp;

/**
 * OCP: New discount policies can be added by creating a new implementation
 * of this interface, without modifying any existing code.
 */
public interface DiscountStrategy {
    double applyDiscount(double amount);
}
