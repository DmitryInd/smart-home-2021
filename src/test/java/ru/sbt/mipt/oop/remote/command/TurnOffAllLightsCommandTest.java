package ru.sbt.mipt.oop.remote.command;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import rc.RemoteControl;
import ru.sbt.mipt.oop.smarthome.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;

class TurnOffAllLightsCommandTest {
    private static final AbstractApplicationContext context
            = new AnnotationConfigApplicationContext(CommandRemoteControlConfiguration.class);

    @Test
    void execute() {
        RemoteControl remoteControl = context.getBean(RemoteControl.class);
        remoteControl.onButtonPressed("A", "Turn off lights control");

        SmartHomeAction checkSmartHomeCondition
                = context.getBean("checkTurnOffAllLightsAction", SmartHomeAction.class);

        context.getBean(SmartHome.class).execute(checkSmartHomeCondition);
    }
}