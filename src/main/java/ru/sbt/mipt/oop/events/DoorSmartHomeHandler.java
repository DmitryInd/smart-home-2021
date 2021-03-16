package ru.sbt.mipt.oop.events;

import javafx.util.Pair;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.*;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class DoorSmartHomeHandler implements SmartHomeHandler {
    private final SmartHome smartHome;
    private final OutputStream output;

    public DoorSmartHomeHandler(SmartHome smartHome, OutputStream output) {
        this.smartHome = smartHome;
        this.output = output;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != LIGHT_ON && event.getType() != LIGHT_OFF) return;
        // событие от двери
        Pair<Door, Room> targetPlace = findDoor(event.getObjectId());
        if (targetPlace != null) {
            moveDoor(targetPlace.getKey(), targetPlace.getValue(), event.getType() == DOOR_OPEN);
        }
    }
    
    private Pair<Door, Room> findDoor(String id) {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(id)) {
                    return new Pair<>(door, room);
                }
            }
        }
        
        return null;
    }
    
    private void moveDoor(Door door, Room room, boolean isOpen) {
        door.setOpen(isOpen);
        output.sendLog("Door " + door.getId() + " in room " + room.getName() + " was "
                + (isOpen? "opened.": "closed."));
    }
}
