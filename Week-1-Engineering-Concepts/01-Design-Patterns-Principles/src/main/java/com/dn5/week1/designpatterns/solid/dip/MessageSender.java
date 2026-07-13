package com.dn5.week1.designpatterns.solid.dip;

/** DIP: high-level code depends on this abstraction, not on concrete senders. */
public interface MessageSender {
    void send(String to, String message);
}
