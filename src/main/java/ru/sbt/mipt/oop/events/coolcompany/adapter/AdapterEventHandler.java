package ru.sbt.mipt.oop.events.coolcompany.adapter;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.events.Handler;

public class AdapterEventHandler implements EventHandler {
    private final Handler handler;

    public AdapterEventHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        try {
            handler.handleEvent(new AdapterSensorEvent(event));
        } catch (RuntimeException ignored) {}
    }
}
