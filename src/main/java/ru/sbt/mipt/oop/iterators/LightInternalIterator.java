package ru.sbt.mipt.oop.iterators;

import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.Light;
import ru.sbt.mipt.oop.smarthome.Room;

public class LightInternalIterator implements InternalIterator {
    private final java.util.Iterator<Light> lights;

    public LightInternalIterator(Room room) {
        this.lights = room.getLights().iterator();
    }

    @Override
    public void performForAll(SmartHomeAction action) {
        lights.forEachRemaining(light -> light.execute(action));
    }
}
