package ru.sbt.mipt.oop.iterators;

import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.Door;

public class DoorInternalIterator implements InternalIterator {
    private final java.util.Iterator<Door> doors;

    public DoorInternalIterator(java.util.Iterator<Door> doors) {
        this.doors = doors;
    }

    @Override
    public void performForAll(SmartHomeAction action) {
        doors.forEachRemaining(door -> door.execute(action));
    }
}
