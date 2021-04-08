package ru.sbt.mipt.oop.events.event;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;

public class AlarmEvent implements Event {
    private final EventType type;
    private final String code;

    public AlarmEvent(EventType type, String code) {
        this.type = type;
        this.code = code;
    }

    @Override
    public EventType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
                "type=" + type +
                ", code='" + code + '\'' +
                '}';
    }
}
