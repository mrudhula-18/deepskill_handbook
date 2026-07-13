package com.dn5.week1.designpatterns.solid.lsp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LspTest {

    @Test
    void rectangleComputesAreaCorrectly() {
        Shape rectangle = new Rectangle(5, 10);
        assertEquals(50.0, rectangle.area(), 0.0001);
    }

    @Test
    void squareComputesAreaCorrectly() {
        Shape square = new Square(5);
        assertEquals(25.0, square.area(), 0.0001);
    }

    @Test
    void bothShapesAreSubstitutableWherevereShapeIsExpected() {
        Shape[] shapes = { new Rectangle(2, 3), new Square(4) };
        double total = 0;
        for (Shape shape : shapes) {
            total += shape.area();
        }
        assertEquals(6.0 + 16.0, total, 0.0001);
    }
}
