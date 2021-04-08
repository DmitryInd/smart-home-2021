package ru.sbt.mipt.oop.remote.command;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import rc.RemoteControl;
import ru.sbt.mipt.oop.alarm.Alarm;

import static org.junit.jupiter.api.Assertions.*;

class TriggerAlarmCommandTest {
    private static final AbstractApplicationContext context
            = new AnnotationConfigApplicationContext(CommandRemoteControlConfiguration.class);

    @Test
    void execute() {
        RemoteControl remoteControl = context.getBean(RemoteControl.class);
        remoteControl.onButtonPressed("D", "Alarm control");

        Alarm alarm = context.getBean(Alarm.class);
        assertTrue(alarm.isDeactivated());
    }
}