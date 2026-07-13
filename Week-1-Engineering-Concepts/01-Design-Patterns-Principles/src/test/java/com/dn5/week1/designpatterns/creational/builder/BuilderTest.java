package com.dn5.week1.designpatterns.creational.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuilderTest {

    @Test
    void builderProducesPizzaWithExpectedProperties() {
        Pizza pizza = new Pizza.Builder()
                .size("large")
                .addTopping("Pepperoni")
                .addTopping("Olives")
                .cheese(true)
                .build();

        assertEquals("large", pizza.getSize());
        assertEquals(2, pizza.getToppings().size());
        assertTrue(pizza.getToppings().contains("Pepperoni"));
        assertTrue(pizza.hasCheese());
        assertTrue(pizza.toString().contains("large"));
    }

    @Test
    void builderHasSensibleDefaults() {
        Pizza pizza = new Pizza.Builder().build();
        assertEquals("medium", pizza.getSize());
        assertTrue(pizza.getToppings().isEmpty());
        assertEquals(false, pizza.hasCheese());
    }
}
