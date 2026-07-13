package com.dn5.week1.designpatterns.behavioral.observer;

public class Investor implements Observer {

    private final String name;
    private double lastKnownPrice = -1;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(double price) {
        this.lastKnownPrice = price;
        System.out.println(name + " notified: new price = " + price);
    }

    public double getLastKnownPrice() {
        return lastKnownPrice;
    }

    public String getName() {
        return name;
    }
}
