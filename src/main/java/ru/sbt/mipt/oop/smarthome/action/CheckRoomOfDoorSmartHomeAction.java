package ru.sbt.mipt.oop.smarthome.action;

import ru.sbt.mipt.oop.smarthome.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.object.Door;
import ru.sbt.mipt.oop.smarthome.object.Room;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public class CheckRoomOfDoorSmartHomeAction implements SmartHomeAction {
    private final String id;
    private final String requiredRoom;
    private final SmartHomeAction toDoAction;
    private String currentRoom;
    private SmartHome smartHome;

    public CheckRoomOfDoorSmartHomeAction(String id, String requiredRoom, SmartHomeAction toDoAction) {
        this.id = id;
        this.requiredRoom = requiredRoom;
        this.toDoAction = toDoAction;
    }

    @Override
    public void performOn(SmartHomeObject object) {
        if (object instanceof SmartHome) {
            smartHome = (SmartHome) object;
        } else if (object instanceof Room) {
            currentRoom = ((Room) object).getName();
        } else if (object instanceof Door) {
            if (id.equals(((Door) object).getId()) && currentRoom.equals(requiredRoom)) {
                smartHome.execute(toDoAction);
            }
        }
    }
}
