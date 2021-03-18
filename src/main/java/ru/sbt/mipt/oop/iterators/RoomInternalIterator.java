package ru.sbt.mipt.oop.iterators;

import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.Room;

public class RoomInternalIterator implements InternalIterator {
    private final java.util.Iterator<Room> rooms;

    public RoomInternalIterator(java.util.Iterator<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public void performForAll(SmartHomeAction action) {
        rooms.forEachRemaining(room -> room.execute(action));
    }
}
