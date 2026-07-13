package com.dn5.week1.designpatterns.structural.decorator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecoratorTest {

    @Test
    void simpleCoffeeHasBaseCostAndDescription() {
        Coffee coffee = new SimpleCoffee();
        assertEquals(2.0, coffee.cost(), 0.0001);
        assertEquals("Coffee", coffee.description());
    }

    @Test
    void singleDecoratorAddsToCostAndDescription() {
        Coffee coffee = new MilkDecorator(new SimpleCoffee());
        assertEquals(2.5, coffee.cost(), 0.0001);
        assertEquals("Coffee + Milk", coffee.description());
    }

    @Test
    void stackingDecoratorsAccumulatesCorrectly() {
        Coffee coffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        assertEquals(2.75, coffee.cost(), 0.0001);
        assertEquals("Coffee + Milk + Sugar", coffee.description());
    }
}
