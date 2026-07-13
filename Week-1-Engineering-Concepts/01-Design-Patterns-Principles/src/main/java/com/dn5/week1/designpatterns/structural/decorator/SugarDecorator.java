package com.dn5.week1.designpatterns.structural.decorator;

public class SugarDecorator extends CoffeeDecorator {

    public SugarDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost() + 0.25;
    }

    @Override
    public String description() {
        return decoratedCoffee.description() + " + Sugar";
    }
}
