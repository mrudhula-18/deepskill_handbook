package com.dn5.week1.designpatterns.solid.lsp;

/** FIX: independent, correct Shape implementation. */
public class Rectangle implements Shape {

    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}
