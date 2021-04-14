package ru.sbt.mipt.oop.events.coolcompany.adapter;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.Handler;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.Map;

public class AdapterEventHandler implements EventHandler {
    private final Handler handler;
    private final Map<String, EventType> dictionary;

    public AdapterEventHandler(Handler handler, Map<String, EventType> dictionary) {
        this.handler = handler;
        this.dictionary = dictionary;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        try {
            handler.handleEvent(new SensorEvent(getEventType(event), event.getObjectId()));
        } catch (RuntimeException ignored) {}
    }

    private EventType getEventType(CCSensorEvent event) {
        EventType eventType = dictionary.get(event.getEventType());
        if (eventType == null) {
            throw new RuntimeException("Wrong type");
        }

        return eventType;
    }
}
