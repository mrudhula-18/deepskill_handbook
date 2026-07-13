package com.dn5.week1.designpatterns.structural.decorator;

/** Base decorator: wraps a {@link Coffee} and delegates by default. */
public abstract class CoffeeDecorator implements Coffee {

    protected final Coffee decoratedCoffee;

    protected CoffeeDecorator(Coffee decoratedCoffee) {
        this.decoratedCoffee = decoratedCoffee;
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost();
    }

    @Override
    public String description() {
        return decoratedCoffee.description();
    }
}
