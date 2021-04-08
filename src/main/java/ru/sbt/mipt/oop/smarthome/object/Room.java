package ru.sbt.mipt.oop.smarthome.object;

import ru.sbt.mipt.oop.smarthome.Actionable;
import ru.sbt.mipt.oop.smarthome.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

import java.util.Collection;

public class Room implements Actionable, SmartHomeObject {
    private final Collection<Light> lights;
    private final Collection<Door> doors;
    private final String name;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void execute(SmartHomeAction smartHomeAction) {
        smartHomeAction.performOn(this);
        doors.forEach(door -> door.execute(smartHomeAction));
        lights.forEach(light -> light.execute(smartHomeAction));
    }
}
