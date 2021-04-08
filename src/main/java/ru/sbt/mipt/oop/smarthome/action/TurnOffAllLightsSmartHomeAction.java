package ru.sbt.mipt.oop.smarthome.action;

import ru.sbt.mipt.oop.command.*;
import ru.sbt.mipt.oop.smarthome.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.object.Light;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public class TurnOffAllLightsSmartHomeAction implements SmartHomeAction {
    private final SenderCommands senderCommands;

    public TurnOffAllLightsSmartHomeAction(SenderCommands senderCommands) {
        this.senderCommands = senderCommands;
    }

    @Override
    public void performOn(SmartHomeObject object) {
        if (object instanceof Light) {
            Light light = (Light) object;
            light.setOn(false);
            senderCommands.sendCommand(new SensorCommand(CommandType.LIGHT_OFF, light.getId()));
        }
    }
}
