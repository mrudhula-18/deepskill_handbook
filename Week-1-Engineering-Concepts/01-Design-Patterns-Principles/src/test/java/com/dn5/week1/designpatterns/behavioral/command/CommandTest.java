package com.dn5.week1.designpatterns.behavioral.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTest {

    @Test
    void pressingOnCommandTurnsLightOn() {
        Light light = new Light();
        RemoteControl remote = new RemoteControl();
        remote.setCommand(new LightOnCommand(light));

        remote.pressButton();

        assertTrue(light.isOn());
    }

    @Test
    void pressingOffCommandTurnsLightOff() {
        Light light = new Light();
        light.turnOn();
        RemoteControl remote = new RemoteControl();
        remote.setCommand(new LightOffCommand(light));

        remote.pressButton();

        assertFalse(light.isOn());
    }

    @Test
    void undoReversesTheLastCommand() {
        Light light = new Light();
        RemoteControl remote = new RemoteControl();
        remote.setCommand(new LightOnCommand(light));
        remote.pressButton();
        assertTrue(light.isOn());

        remote.pressUndo();

        assertFalse(light.isOn());
    }
}
