package com.dn5.week1.designpatterns.structural.decorator;

public class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost() + 0.5;
    }

    @Override
    public String description() {
        return decoratedCoffee.description() + " + Milk";
    }
}
