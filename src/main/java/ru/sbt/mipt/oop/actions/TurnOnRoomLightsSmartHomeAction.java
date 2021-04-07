package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.command.*;
import ru.sbt.mipt.oop.smarthome.Light;
import ru.sbt.mipt.oop.smarthome.Room;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public class TurnOnRoomLightsSmartHomeAction implements SmartHomeAction {
    private final SenderCommands senderCommands;
    private final String requiredRoom;
    private String currentRoom;

    public TurnOnRoomLightsSmartHomeAction(String requiredRoom, SenderCommands senderCommands) {
        this.requiredRoom = requiredRoom;
        this.senderCommands = senderCommands;
    }

    @Override
    public void performOn(SmartHomeObject object) {
        if (object instanceof Room) {
            currentRoom = ((Room) object).getName();
        } else if (object instanceof Light) {
            if (currentRoom.equals(requiredRoom)) {
                Light light = (Light) object;
                light.setOn(true);
                senderCommands.sendCommand(new SensorCommand(CommandType.LIGHT_ON, light.getId()));
            }
        }
    }
}