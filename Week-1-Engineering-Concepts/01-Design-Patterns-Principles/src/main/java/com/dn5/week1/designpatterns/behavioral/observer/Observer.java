package com.dn5.week1.designpatterns.behavioral.observer;

/** Observer contract - deliberately NOT java.util.Observer (deprecated). */
public interface Observer {
    void update(double price);
}
