package ru.sbt.mipt.oop.iterators;

import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.Door;
import ru.sbt.mipt.oop.smarthome.Room;

public class DoorInternalIterator implements InternalIterator {
    private final java.util.Iterator<Door> doors;

    public DoorInternalIterator(Room room) {
        this.doors = room.getDoors().iterator();
    }

    @Override
    public void performForAll(SmartHomeAction action) {
        doors.forEachRemaining(door -> door.execute(action));

    }
}
