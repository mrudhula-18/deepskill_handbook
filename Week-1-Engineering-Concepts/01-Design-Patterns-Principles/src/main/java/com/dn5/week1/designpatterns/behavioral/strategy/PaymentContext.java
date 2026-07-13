package com.dn5.week1.designpatterns.behavioral.strategy;

public class PaymentContext {

    private PaymentStrategy strategy;

    public PaymentContext() {
    }

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public String executePayment(double amount) {
        if (strategy == null) {
            throw new IllegalStateException("No payment strategy set");
        }
        return strategy.pay(amount);
    }
}
