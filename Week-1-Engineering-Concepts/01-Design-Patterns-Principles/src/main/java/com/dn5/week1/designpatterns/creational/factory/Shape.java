package com.dn5.week1.designpatterns.creational.factory;

/**
 * Local Shape abstraction for the factory pattern example. Distinct from
 * com.dn5.week1.designpatterns.solid.lsp.Shape - different package, no conflict.
 */
public interface Shape {
    double area();

    String describe();
}
