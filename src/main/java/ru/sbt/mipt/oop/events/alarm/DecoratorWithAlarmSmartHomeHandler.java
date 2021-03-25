package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;

import static ru.sbt.mipt.oop.events.alarm.AlarmStatus.*;

public class DecoratorWithAlarmSmartHomeHandler implements SmartHomeHandler {
    private final SmartHomeHandler handler;
    private final AlarmSmartHomeHandler alarm;

    public DecoratorWithAlarmSmartHomeHandler(SmartHomeHandler handler, AlarmSmartHomeHandler alarm) {
        this.handler = handler;
        this.alarm = alarm;
    }

    @Override
    public void handleEvent(Event event) {
        alarm.handleEvent(event);
        if (alarm.getStatus() == DEACTIVATED) {
            handler.handleEvent(event);
        }
    }
}
