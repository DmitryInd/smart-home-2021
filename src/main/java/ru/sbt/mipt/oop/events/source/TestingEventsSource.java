package ru.sbt.mipt.oop.events.source;

import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.EventsSource;
import ru.sbt.mipt.oop.events.event.SensorEvent;

public class TestingEventsSource implements EventsSource {
    @Override
    public SensorEvent getNextSensorEvent() {
        // pretend like we're getting the events from physical world, but here we're going to just generate some random events
        if (Math.random() < 0.05) return null; // null means end of event stream
        EventType eventType = EventType.values()[(int) (4 * Math.random())];
        String objectId = "" + ((int) (10 * Math.random()));
        return new SensorEvent(eventType, objectId);
    }
}