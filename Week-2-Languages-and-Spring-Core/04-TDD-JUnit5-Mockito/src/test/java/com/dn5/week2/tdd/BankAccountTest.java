package com.dn5.week2.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link BankAccount} written in strict Arrange-Act-Assert (AAA) style.
 */
class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        // Arrange
        account = new BankAccount(100.0);
    }

    @Test
    @DisplayName("deposit() should increase the balance by the deposited amount")
    void deposit_increasesBalance() {
        // Arrange
        double depositAmount = 50.0;

        // Act
        account.deposit(depositAmount);

        // Assert
        assertEquals(150.0, account.getBalance(), 0.0001);
    }

    @Test
    @DisplayName("withdraw() should decrease the balance when funds are sufficient")
    void withdraw_withSufficientFunds_decreasesBalance() {
        // Arrange
        double withdrawAmount = 40.0;

        // Act
        account.withdraw(withdrawAmount);

        // Assert
        assertEquals(60.0, account.getBalance(), 0.0001);
    }

    @Test
    @DisplayName("withdraw() should throw InsufficientFundsException when amount exceeds balance")
    void withdraw_withInsufficientFunds_throwsException() {
        // Arrange
        double withdrawAmount = 500.0;

        // Act & Assert
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> account.withdraw(withdrawAmount));
        assertEquals("Cannot withdraw 500.0; current balance is 100.0", exception.getMessage());
        // Balance should remain unchanged after a failed withdrawal
        assertEquals(100.0, account.getBalance(), 0.0001);
    }
}
