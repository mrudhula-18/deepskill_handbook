package com.dn5.week1.designpatterns.creational.factory;

public class Square implements Shape {

    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double area() {
        return side * side;
    }

    @Override
    public String describe() {
        return "Square(side=" + side + ")";
    }
}
