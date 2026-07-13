package com.dn5.week1.designpatterns.solid.lsp;

/**
 * FIX: Rectangle and Square are modeled as independent, correct
 * implementations of a common abstraction instead of one being forced to
 * extend the other.
 */
public interface Shape {
    double area();
}
