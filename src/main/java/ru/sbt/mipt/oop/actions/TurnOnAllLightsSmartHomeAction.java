package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.command.*;
import ru.sbt.mipt.oop.smarthome.Light;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public class TurnOnAllLightsSmartHomeAction implements SmartHomeAction {
    private final SenderCommands senderCommands;

    public TurnOnAllLightsSmartHomeAction(SenderCommands senderCommands) {
        this.senderCommands = senderCommands;
    }

    @Override
    public void performOn(SmartHomeObject object) {
        if (object instanceof Light) {
            Light light = (Light) object;
            light.setOn(true);
            senderCommands.sendCommand(new SensorCommand(CommandType.LIGHT_ON, light.getId()));
        }
    }
}
