package ru.sbt.mipt.oop.rc.command;

import ru.sbt.mipt.oop.actions.TurnOnRoomLightsSmartHomeAction;
import ru.sbt.mipt.oop.command.SenderCommands;
import ru.sbt.mipt.oop.smarthome.SmartHome;

public class TurnOnHallLights implements Command {
    private final SmartHome smartHome;
    private final SenderCommands senderCommands;

    TurnOnHallLights(SmartHome smartHome, SenderCommands senderCommands) {
        this.smartHome = smartHome;
        this.senderCommands = senderCommands;
    }

    @Override
    public void execute() {
        smartHome.execute(new TurnOnRoomLightsSmartHomeAction("Hall", senderCommands));
    }
}