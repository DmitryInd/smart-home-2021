package ru.sbt.mipt.oop.smarthome;

import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.actions.SmartHomeAction;

import java.util.Collection;

public class Room implements Actionable, SmartHomeObject {
    private Collection<Light> lights;
    private Collection<Door> doors;
    private String name;

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
