package ru.sbt.mipt.oop.events.coolcompany.adapter;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;

public class AdapterSensorEvent extends SensorEvent {

    public AdapterSensorEvent(CCSensorEvent event) {
        super(getEventType(event), event.getObjectId());
    }

    private static EventType getEventType(CCSensorEvent event) {
        EventType eventType;
        switch (event.getEventType()) {
            case "LightIsOn":
                eventType = EventType.LIGHT_ON;
                break;
            case "LightIsOff":
                eventType = EventType.LIGHT_OFF;
                break;
            case "DoorIsOpen":
                eventType = EventType.DOOR_OPEN;
                break;
            case "DoorIsClosed":
                eventType = EventType.DOOR_CLOSED;
                break;
            default:
                throw new RuntimeException("Wrong type");
        }

        return eventType;
    }
}
