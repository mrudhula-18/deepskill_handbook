package com.dn5.week1.designpatterns.creational.factory;

/**
 * Factory Method: centralizes creation of {@link Shape} instances so callers
 * depend only on {@link ShapeType} and this factory, not on the concrete
 * classes.
 */
public class ShapeFactory {

    public Shape createShape(ShapeType type, double... dims) {
        switch (type) {
            case CIRCLE:
                return new Circle(dims[0]);
            case SQUARE:
                return new Square(dims[0]);
            case TRIANGLE:
                return new Triangle(dims[0], dims[1]);
            default:
                throw new IllegalArgumentException("Unknown shape type: " + type);
        }
    }
}
