package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.*;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class DoorSmartHomeHandler extends SmartHomeHandler {

    public DoorSmartHomeHandler(SmartHome smartHome, OutputStream output) {
        super(smartHome, output);
    }

    @Override
    public void handleEvent(SensorEvent event) {
        // событие от двери
        Door door = findDoor(event.getObjectId());
        if (door != null) {
            moveDoor(currentRoom, door, event.getType() == DOOR_OPEN);
            if (event.getType() == DOOR_CLOSED && currentRoom.getName().equals("hall")) {
                // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
                // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
                ScriptOfSmartHome turnOffAllLights = new turnOfLightsScriptOfSmartHome(smartHome);
                turnOffAllLights.execute();
            }
        }
    }

    private Room currentRoom = null;
    
    private Door findDoor(String id) {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(id)) {
                    currentRoom = room;
                    return door;
                }
            }
        }
        
        return null;
    }
    
    private void moveDoor(Room room, Door door, boolean isOpen) {
        door.setOpen(isOpen);
        output.sendLog("Door " + door.getId() + " in room " + room.getName() + " was "
                + (isOpen? "opened.": "closed."));
    }
}
