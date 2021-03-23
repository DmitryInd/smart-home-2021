package ru.sbt.mipt.oop.smarthome;

import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.actions.SmartHomeAction;

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

    @Override
    public void execute(SmartHomeAction smartHomeAction) {
        rooms.forEach(room -> room.execute(smartHomeAction));
    }
}
