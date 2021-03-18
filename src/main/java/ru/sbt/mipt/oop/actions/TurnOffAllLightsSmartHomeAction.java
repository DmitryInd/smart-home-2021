package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.command.*;
import ru.sbt.mipt.oop.smarthome.Light;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public class TurnOffAllLightsSmartHomeAction implements SmartHomeAction {
    @Override
    public void performOn(SmartHomeObject object) {
        if (object instanceof Light) {
            Light light = (Light) object;
            light.setOn(false);
            SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
            SenderCommands senderCommands = new DummySenderCommands();
            senderCommands.sendCommand(command);
        }
    }
}
