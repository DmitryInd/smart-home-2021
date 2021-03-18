package ru.sbt.mipt.oop.smarthome;

import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.iterators.*;

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
        createDoorIterator().performForAll(smartHomeAction);
        createLightIterator().performForAll(smartHomeAction);
    }

    public InternalIterator createDoorIterator() {
        return new DoorInternalIterator(doors.iterator());
    }

    public InternalIterator createLightIterator() {
        return new LightInternalIterator(lights.iterator());
    }
}
