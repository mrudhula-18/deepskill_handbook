package com.dn5.week1.designpatterns.solid.dip;

/**
 * DIP: high-level module depends only on the MessageSender abstraction,
 * which is injected through the constructor - it never depends on any
 * concrete low-level implementation.
 */
public class NotificationService {

    private final MessageSender messageSender;

    public NotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void notify(String to, String message) {
        messageSender.send(to, message);
    }
}
