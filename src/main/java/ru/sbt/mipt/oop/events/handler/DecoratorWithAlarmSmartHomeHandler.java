package ru.sbt.mipt.oop.events.handler;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.events.event.AlarmEvent;
import ru.sbt.mipt.oop.notification.SenderNotifications;

public class DecoratorWithAlarmSmartHomeHandler implements SmartHomeHandler {
    private final SenderNotifications senderNotifications;
    private final SmartHomeHandler handler;
    private final Alarm alarm;

    public DecoratorWithAlarmSmartHomeHandler(SenderNotifications senderNotifications, SmartHomeHandler handler, Alarm alarm) {
        this.senderNotifications = senderNotifications;
        this.handler = handler;
        this.alarm = alarm;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof AlarmEvent || alarm.isDeactivated()) {
            handler.handleEvent(event);
        } else {
            alarm.trigger();
            senderNotifications.send("Intruder has been detected");
        }
    }
}
