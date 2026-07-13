package com.dn5.week1.designpatterns.solid.ocp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountCalculatorTest {

    @Test
    void regularCustomerGetsFivePercentOff() {
        DiscountCalculator calculator = new DiscountCalculator(new RegularCustomerDiscount());
        assertEquals(95.0, calculator.computeFinalPrice(100.0), 0.0001);
    }

    @Test
    void vipCustomerGetsTwentyPercentOff() {
        DiscountCalculator calculator = new DiscountCalculator(new VipCustomerDiscount());
        assertEquals(80.0, calculator.computeFinalPrice(100.0), 0.0001);
    }

    @Test
    void calculatorWorksWithAnyStrategyWithoutModification() {
        DiscountStrategy noOpDiscount = amount -> amount;
        DiscountCalculator calculator = new DiscountCalculator(noOpDiscount);
        assertEquals(50.0, calculator.computeFinalPrice(50.0), 0.0001);
    }
}
