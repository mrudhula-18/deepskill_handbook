package com.dn5.week1.designpatterns.creational.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FactoryTest {

    private final ShapeFactory factory = new ShapeFactory();

    @Test
    void createsCircleWithCorrectArea() {
        Shape circle = factory.createShape(ShapeType.CIRCLE, 2.0);
        assertTrue(circle instanceof Circle);
        assertEquals(Math.PI * 4, circle.area(), 0.0001);
    }

    @Test
    void createsSquareWithCorrectArea() {
        Shape square = factory.createShape(ShapeType.SQUARE, 3.0);
        assertTrue(square instanceof Square);
        assertEquals(9.0, square.area(), 0.0001);
    }

    @Test
    void createsTriangleWithCorrectArea() {
        Shape triangle = factory.createShape(ShapeType.TRIANGLE, 4.0, 5.0);
        assertTrue(triangle instanceof Triangle);
        assertEquals(10.0, triangle.area(), 0.0001);
    }
}
