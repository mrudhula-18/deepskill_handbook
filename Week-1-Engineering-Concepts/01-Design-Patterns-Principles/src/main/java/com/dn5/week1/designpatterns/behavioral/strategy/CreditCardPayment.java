package com.dn5.week1.designpatterns.behavioral.strategy;

public class CreditCardPayment implements PaymentStrategy {

    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String pay(double amount) {
        return "Paid " + amount + " using credit card ending in "
                + cardNumber.substring(Math.max(0, cardNumber.length() - 4));
    }
}
