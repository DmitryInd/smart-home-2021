package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.smarthome.Door;
import ru.sbt.mipt.oop.smarthome.Room;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public class GetDoorRoomSmartHomeAction implements SmartHomeAction {
    private String currentRoom;
    private final StringBuilder roomName;
    private final String id;

    public GetDoorRoomSmartHomeAction(StringBuilder roomName, String id) {
        this.roomName = roomName;
        this.id = id;
    }

    @Override
    public void performOn(SmartHomeObject object) {
        if (object instanceof Room) {
            currentRoom = ((Room) object).getName();
        } else if (object instanceof Door) {
            if (id.equals(((Door) object).getId())) {
                roomName.delete(0, roomName.length());
                roomName.append(currentRoom);
            }
        }
    }
}
