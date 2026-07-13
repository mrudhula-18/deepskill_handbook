package com.dn5.week1.designpatterns.behavioral.command;

/** The receiver: knows how to actually perform the work. */
public class Light {

    private boolean on = false;

    public void turnOn() {
        on = true;
        System.out.println("Light is ON");
    }

    public void turnOff() {
        on = false;
        System.out.println("Light is OFF");
    }

    public boolean isOn() {
        return on;
    }
}
