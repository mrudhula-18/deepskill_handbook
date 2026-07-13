package com.dn5.week1.designpatterns.creational.factory;

public class Triangle implements Shape {

    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }

    @Override
    public String describe() {
        return "Triangle(base=" + base + ", height=" + height + ")";
    }
}
