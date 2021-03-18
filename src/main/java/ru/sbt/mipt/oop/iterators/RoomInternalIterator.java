package ru.sbt.mipt.oop.iterators;

import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.Room;
import ru.sbt.mipt.oop.smarthome.SmartHome;

public class RoomInternalIterator implements InternalIterator {
    private final java.util.Iterator<Room> rooms;

    public RoomInternalIterator(SmartHome smartHome) {
        this.rooms = smartHome.getRooms().iterator();
    }

    @Override
    public void performForAll(SmartHomeAction action) {
        rooms.forEachRemaining(room -> room.execute(action));
    }
}
