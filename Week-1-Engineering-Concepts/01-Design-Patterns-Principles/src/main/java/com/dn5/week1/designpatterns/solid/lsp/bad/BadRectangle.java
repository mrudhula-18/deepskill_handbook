package com.dn5.week1.designpatterns.solid.lsp.bad;

/**
 * ANTIPATTERN setup: a classic mutable rectangle.
 */
public class BadRectangle {

    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArea() {
        return width * height;
    }
}
