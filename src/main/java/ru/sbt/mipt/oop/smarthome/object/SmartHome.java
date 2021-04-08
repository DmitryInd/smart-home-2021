package ru.sbt.mipt.oop.smarthome.object;

import ru.sbt.mipt.oop.smarthome.Actionable;
import ru.sbt.mipt.oop.smarthome.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

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
        smartHomeAction.performOn(this);
        rooms.forEach(room -> room.execute(smartHomeAction));
    }
}
