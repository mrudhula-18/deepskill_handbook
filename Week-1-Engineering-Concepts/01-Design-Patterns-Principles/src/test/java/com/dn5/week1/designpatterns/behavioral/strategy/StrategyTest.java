package com.dn5.week1.designpatterns.behavioral.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StrategyTest {

    @Test
    void contextDelegatesToCreditCardStrategy() {
        PaymentContext context = new PaymentContext(new CreditCardPayment("4111111111111234"));
        String receipt = context.executePayment(100);
        assertTrue(receipt.contains("credit card"));
        assertTrue(receipt.contains("1234"));
    }

    @Test
    void contextDelegatesToPaypalStrategyAfterSwitching() {
        PaymentContext context = new PaymentContext(new CreditCardPayment("4111111111111234"));
        context.setStrategy(new PaypalPayment("carol@example.com"));

        String receipt = context.executePayment(50);

        assertTrue(receipt.contains("PayPal"));
        assertTrue(receipt.contains("carol@example.com"));
    }

    @Test
    void contextWithoutStrategySetThrows() {
        PaymentContext context = new PaymentContext();
        assertThrows(IllegalStateException.class, () -> context.executePayment(10));
    }
}
