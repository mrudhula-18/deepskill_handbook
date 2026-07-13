package com.dn5.week1.designpatterns.behavioral.command;

/** The invoker: holds a command and triggers it, without knowing the receiver. */
public class RemoteControl {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        if (command != null) {
            command.execute();
        }
    }

    public void pressUndo() {
        if (command != null) {
            command.undo();
        }
    }
}
