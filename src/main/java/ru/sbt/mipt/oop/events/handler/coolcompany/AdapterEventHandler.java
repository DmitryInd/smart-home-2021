package ru.sbt.mipt.oop.events.handler.coolcompany;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.Handler;
import ru.sbt.mipt.oop.events.event.coolcompany.AdapterSensorEvent;

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
            handler.handleEvent(new AdapterSensorEvent(event, dictionary));
        } catch (RuntimeException ignored) {}
    }
}
