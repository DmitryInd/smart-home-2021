package ru.sbt.mipt.oop.events;

public interface SmartHomeHandler extends Handler {
    @Override
    void handleEvent(SensorEvent event);
}
