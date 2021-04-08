package ru.sbt.mipt.oop.remote.command;

import ru.sbt.mipt.oop.smarthome.action.TurnOnAllLightsSmartHomeAction;
import ru.sbt.mipt.oop.command.SenderCommands;
import ru.sbt.mipt.oop.remote.Command;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;

public class TurnOnAllLights implements Command {
    private final SmartHome smartHome;
    private final SenderCommands senderCommands;

    TurnOnAllLights(SmartHome smartHome, SenderCommands senderCommands) {
        this.smartHome = smartHome;
        this.senderCommands = senderCommands;
    }

    @Override
    public void execute() {
        smartHome.execute(new TurnOnAllLightsSmartHomeAction(senderCommands));
    }
}