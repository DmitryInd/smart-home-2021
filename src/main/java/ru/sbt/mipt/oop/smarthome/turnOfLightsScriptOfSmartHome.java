package ru.sbt.mipt.oop.smarthome;

import ru.sbt.mipt.oop.command.*;

public class turnOfLightsScriptOfSmartHome implements ScriptOfSmartHome {
    public turnOfLightsScriptOfSmartHome(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        for (Room homeRoom : smartHome.getRooms()) {
            for (Light light : homeRoom.getLights()) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                SenderCommands senderCommands = new DummySenderCommands();
                senderCommands.sendCommand(command);
            }
        }
    }

    private final SmartHome smartHome;
}
