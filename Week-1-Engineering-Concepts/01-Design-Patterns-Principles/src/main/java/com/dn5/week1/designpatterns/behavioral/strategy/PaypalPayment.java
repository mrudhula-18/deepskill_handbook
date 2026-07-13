package com.dn5.week1.designpatterns.behavioral.strategy;

public class PaypalPayment implements PaymentStrategy {

    private final String email;

    public PaypalPayment(String email) {
        this.email = email;
    }

    @Override
    public String pay(double amount) {
        return "Paid " + amount + " using PayPal account " + email;
    }
}
