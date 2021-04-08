package ru.sbt.mipt.oop.remote.control;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import rc.RemoteControl;

import static org.junit.jupiter.api.Assertions.*;

class SmartHomeRemoteControlTest {
    private final static AbstractApplicationContext context
            = new AnnotationConfigApplicationContext(DummyRemoteControlConfiguration.class);

    @Test
    void callFirstRemoteControlTest() {
        RemoteControl remoteControl = context.getBean(RemoteControl.class);
        try {
            remoteControl.onButtonPressed("C", "1");
            fail();
        } catch (RuntimeException e) {
            assertEquals("1C", e.getMessage());
        }

        try {
            remoteControl.onButtonPressed("D", "1");
            fail();
        } catch (RuntimeException e) {
            assertEquals("1D", e.getMessage());
        }
    }

    @Test
    void callNotFirstRemoteControlTest() {
        RemoteControl remoteControl = context.getBean(RemoteControl.class);
        try {
            remoteControl.onButtonPressed("A", "2");
            fail();
        } catch (RuntimeException e) {
            assertEquals("2A", e.getMessage());
        }

        try {
            remoteControl.onButtonPressed("B", "2");
            fail();
        } catch (RuntimeException e) {
            assertEquals("2B", e.getMessage());
        }

        try {
            remoteControl.onButtonPressed("A", "4");
            fail();
        } catch (RuntimeException e) {
            assertEquals("4A", e.getMessage());
        }
    }

    @Test
    void callNotExistingRemoteControlTest() {
        RemoteControl remoteControl = context.getBean(RemoteControl.class);
        remoteControl.onButtonPressed("E", "6");
        remoteControl.onButtonPressed("F", "1");
        remoteControl.onButtonPressed("A", "-1");
    }
}