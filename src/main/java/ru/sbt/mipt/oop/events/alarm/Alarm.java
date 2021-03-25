package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;

public interface Alarm {
    void handleEvent(SmartHomeHandler handler, Event event);
}
