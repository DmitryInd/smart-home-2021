package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.SmartHome;

public abstract class SmartHomeHandler implements Handler {
    public SmartHomeHandler(SmartHome smartHome, OutputStream output) {
        this.smartHome = smartHome;
        this.output = output;
    }

    @Override
    public abstract void handleEvent(SensorEvent event);

    protected SmartHome smartHome;
    protected OutputStream output;
}
