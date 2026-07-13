package com.dn5.week2.tdd;

/**
 * Thrown when a withdrawal is attempted for an amount greater than the
 * current balance of a {@link BankAccount}.
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
