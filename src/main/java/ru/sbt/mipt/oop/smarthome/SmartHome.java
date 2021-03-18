package ru.sbt.mipt.oop.smarthome;

import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.iterators.InternalIterator;
import ru.sbt.mipt.oop.iterators.RoomInternalIterator;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements Actionable, SmartHomeObject {
    Collection<Room> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) { rooms.add(room); }

    @Override
    public void execute(SmartHomeAction smartHomeAction) {
        smartHomeAction.performOn(this);
        createIterator().performForAll(smartHomeAction);
    }

    public InternalIterator createIterator() {
        return new RoomInternalIterator(rooms.iterator());
    }
}
