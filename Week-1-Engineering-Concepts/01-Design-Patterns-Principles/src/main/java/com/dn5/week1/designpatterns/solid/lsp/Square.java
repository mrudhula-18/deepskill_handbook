package com.dn5.week1.designpatterns.solid.lsp;

/**
 * FIX: independent, correct Shape implementation. Square does NOT extend
 * Rectangle - each shape implements the common Shape contract on its own
 * terms, so no subtype needs to override behavior in a surprising way.
 */
public class Square implements Shape {

    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double area() {
        return side * side;
    }
}
