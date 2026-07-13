package com.dn5.week1.designpatterns.solid.lsp.bad;

/**
 * ANTIPATTERN: violates the Liskov Substitution Principle.
 * <p>
 * A BadSquare "is-a" BadRectangle syntactically, but forcing width == height
 * means it does not behave like a BadRectangle from the caller's point of
 * view. A method that takes a BadRectangle, sets width=5 and height=10, and
 * expects area == 50 will get area == 100 when handed a BadSquare instead -
 * silently breaking the caller's expectations. Subtypes must be substitutable
 * for their base type without altering correctness; this one is not.
 */
public class BadSquare extends BadRectangle {

    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width; // side effect the caller never asked for
    }

    @Override
    public void setHeight(int height) {
        this.width = height; // side effect the caller never asked for
        this.height = height;
    }
}
