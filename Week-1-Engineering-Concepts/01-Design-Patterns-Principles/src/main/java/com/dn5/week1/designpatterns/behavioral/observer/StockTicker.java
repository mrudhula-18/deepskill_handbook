package com.dn5.week1.designpatterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class StockTicker implements Subject {

    private final List<Observer> observers = new ArrayList<>();
    private double price;

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(price);
        }
    }

    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }

    public double getPrice() {
        return price;
    }
}
