package ru.sbt.mipt.oop.events.event.coolcompany;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.event.SensorEvent;

import java.util.Map;

public class AdapterSensorEvent extends SensorEvent {
    public AdapterSensorEvent(CCSensorEvent event, Map<String, EventType> dictionary) {
        super(getEventType(event, dictionary), event.getObjectId());
    }

    private static EventType getEventType(CCSensorEvent event, Map<String, EventType> dictionary) {
        EventType eventType = dictionary.get(event.getEventType());
        if (eventType == null) {
            throw new RuntimeException("Wrong type");
        }

        return eventType;
    }
}
