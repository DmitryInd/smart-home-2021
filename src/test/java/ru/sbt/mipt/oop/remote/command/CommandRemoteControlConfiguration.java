package ru.sbt.mipt.oop.remote.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControl;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.SmartHomeAlarm;
import ru.sbt.mipt.oop.command.DummySenderCommands;
import ru.sbt.mipt.oop.command.SenderCommands;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.remote.Command;
import ru.sbt.mipt.oop.remote.RemoteControlBuilder;
import ru.sbt.mipt.oop.remote.control.SmartHomeRemoteControl;
import ru.sbt.mipt.oop.remote.control.SmartHomeRemoteControlBuilder;
import ru.sbt.mipt.oop.smarthome.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;
import ru.sbt.mipt.oop.smarthome.object.*;
import ru.sbt.mipt.oop.smarthome.object.recorder.JsonSmartHomeRecorder;

import static org.junit.jupiter.api.Assertions.*;

@Configuration
public class CommandRemoteControlConfiguration {
    @Bean
    RemoteControl remoteControl() {
        RemoteControlBuilder<SmartHomeRemoteControl> builder = new SmartHomeRemoteControlBuilder();
        builder.setRcId("Turn off lights control");
        builder.setEventForButton("A", turnOffAllLightsCommand());
        builder.getResult();

        builder.setRcId("Turn on lights control");
        builder.setEventForButton("A", turnOnAllLightsCommand());
        builder.getResult();

        builder.setRcId("Hall control");
        builder.setEventForButton("A", turnHallAllLightsCommand());
        builder.setEventForButton("B", closeEntryDoorCommand());
        builder.getResult();

        builder.setRcId("Alarm control");
        builder.setEventForButton("C", activateAlarmCommand());
        builder.setEventForButton("D", triggerAlarmCommand());
        return builder.getResult();
    }

    @Bean
    SmartHomeAction checkTurnOnAllLightsAction() {
        return object -> {
            if (object instanceof Light) {
                Light lights = (Light) object;
                assertTrue(lights.isOn());
            }
        };
    }

    @Bean
    SmartHomeAction checkTurnOffAllLightsAction() {
        return object -> {
            if (object instanceof Light) {
                Light lights = (Light) object;
                assertFalse(lights.isOn());
            }
        };
    }

    @Bean
    SmartHomeAction checkHallLightsAction() {
        return new SmartHomeAction() {
            private String currentRoom;

            @Override
            public void performOn(SmartHomeObject object) {
                if (object instanceof Room) {
                    currentRoom = ((Room) object).getName();
                } else if (object instanceof Light && currentRoom.equals("Hall")) {
                    Light lights = (Light) object;
                    assertTrue(lights.isOn());
                }
            }
        };
    }

    @Bean
    SmartHomeAction checkEntryDoorAction() {
        return object -> {
            if (object instanceof Door) {
                Door door = (Door) object;
                if (door.getId().equals("1")) {
                    assertFalse(door.isOpen());
                }
            }
        };
    }

    @Bean
    Command turnOffAllLightsCommand() {
        return new TurnOffAllLightsCommand(smartHome(), smsSenderCommands());
    }

    @Bean
    Command turnOnAllLightsCommand() {
        return new TurnOnAllLightsCommand(smartHome(), smsSenderCommands());
    }

    @Bean
    Command turnHallAllLightsCommand() {
        return new TurnOffAllLightsCommand(smartHome(), smsSenderCommands());
    }

    @Bean
    Command activateAlarmCommand() {
        return new ActivateAlarmCommand(smartHomeAlarm(), "Here is Johny!");
    }

    @Bean
    Command triggerAlarmCommand() {
        return new TriggerAlarmCommand(smartHomeAlarm());
    }

    @Bean
    Command closeEntryDoorCommand() {
        return new CloseEntryDoorCommand(smartHome(), "1", consoleOutputStream());
    }

    @Bean
    SenderCommands smsSenderCommands() {
        return new DummySenderCommands();
    }

    @Bean
    SmartHome smartHome() {
        SmartHomeRecorder homeReader = new JsonSmartHomeRecorder("smart-home-1.js");
        return homeReader.readSmartHome();
    }

    @Bean
    Alarm smartHomeAlarm() {
        return new SmartHomeAlarm();
    }

    @Bean
    OutputStream consoleOutputStream() {
        return new ConsoleOutputStream();
    }
}
