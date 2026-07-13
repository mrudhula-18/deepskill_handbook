package com.dn5.week1.designpatterns.solid.dip;

/** Low-level detail implementing the MessageSender abstraction. */
public class EmailSender implements MessageSender {
    @Override
    public void send(String to, String message) {
        System.out.println("Email to " + to + ": " + message);
    }
}
