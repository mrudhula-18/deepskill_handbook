package com.dn5.week1.designpatterns.structural.decorator;

public class SimpleCoffee implements Coffee {
    @Override
    public double cost() {
        return 2.0;
    }

    @Override
    public String description() {
        return "Coffee";
    }
}
