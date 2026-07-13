package com.dn5.week1.designpatterns.behavioral.observer;

/** Subject contract - deliberately NOT java.util.Observable (deprecated). */
public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
