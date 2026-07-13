package com.dn5.week1.designpatterns.solid.dip;

/** Low-level detail implementing the MessageSender abstraction. */
public class SmsSender implements MessageSender {
    @Override
    public void send(String to, String message) {
        System.out.println("SMS to " + to + ": " + message);
    }
}
