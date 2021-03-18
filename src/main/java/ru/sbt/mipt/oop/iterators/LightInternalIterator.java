package ru.sbt.mipt.oop.iterators;

import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.Light;

public class LightInternalIterator implements InternalIterator {
    private final java.util.Iterator<Light> lights;

    public LightInternalIterator(java.util.Iterator<Light> lights) {
        this.lights = lights;
    }

    @Override
    public void performForAll(SmartHomeAction action) {
        lights.forEachRemaining(light -> light.execute(action));
    }
}
