package com.dn5.week2.tdd;

/**
 * A minimal bank account supporting deposits and withdrawals.
 */
public class BankAccount {

    private double balance;

    public BankAccount() {
        this(0.0);
    }

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + "; current balance is " + balance);
        }
        this.balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
