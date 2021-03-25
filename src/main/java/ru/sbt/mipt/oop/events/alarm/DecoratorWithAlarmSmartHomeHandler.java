package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;

public class DecoratorWithAlarmSmartHomeHandler implements SmartHomeHandler {
    private final SmartHomeHandler handler;
    private final Alarm alarm;

    public DecoratorWithAlarmSmartHomeHandler(SmartHomeHandler handler, Alarm alarm) {
        this.handler = handler;
        this.alarm = alarm;
    }

    @Override
    public void handleEvent(Event event) {
        alarm.handleEvent(handler, event);
    }
}
