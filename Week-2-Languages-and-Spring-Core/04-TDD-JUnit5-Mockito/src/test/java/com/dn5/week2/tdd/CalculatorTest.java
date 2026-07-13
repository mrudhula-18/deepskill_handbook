package com.dn5.week2.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link Calculator} written in strict Arrange-Act-Assert (AAA) style.
 */
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        // Arrange (shared fixture)
        calculator = new Calculator();
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
            "1, 1, 2",
            "2, 3, 5",
            "-1, 1, 0",
            "0, 0, 0",
            "10.5, 4.5, 15.0"
    })
    @DisplayName("add() should return the sum of two numbers")
    void add_returnsSumOfTwoNumbers(double a, double b, double expected) {
        // Arrange - values supplied by @CsvSource

        // Act
        double result = calculator.add(a, b);

        // Assert
        assertEquals(expected, result, 0.0001);
    }

    @Test
    @DisplayName("subtract() should return the difference of two numbers")
    void subtract_returnsDifference() {
        // Arrange
        double a = 10;
        double b = 4;

        // Act
        double result = calculator.subtract(a, b);

        // Assert
        assertEquals(6, result, 0.0001);
    }

    @Test
    @DisplayName("multiply() should return the product of two numbers")
    void multiply_returnsProduct() {
        // Arrange
        double a = 6;
        double b = 7;

        // Act
        double result = calculator.multiply(a, b);

        // Assert
        assertEquals(42, result, 0.0001);
    }

    @Test
    @DisplayName("divide() should return the quotient of two numbers")
    void divide_returnsQuotient() {
        // Arrange
        double a = 20;
        double b = 4;

        // Act
        double result = calculator.divide(a, b);

        // Assert
        assertEquals(5, result, 0.0001);
    }

    @Test
    @DisplayName("divide() should throw ArithmeticException when dividing by zero")
    void divide_byZero_throwsArithmeticException() {
        // Arrange
        double a = 10;
        double b = 0;

        // Act & Assert
        ArithmeticException exception = assertThrows(ArithmeticException.class,
                () -> calculator.divide(a, b));
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }
}
