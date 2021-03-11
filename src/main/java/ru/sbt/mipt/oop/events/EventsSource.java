package ru.sbt.mipt.oop.events;

public interface EventsSource {
    SensorEvent getNextSensorEvent();
}
