package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.notification.SenderNotifications;

public class BaseAlarm implements Alarm {
    private final SenderNotifications senderNotifications;
    private Alarm condition;

    public BaseAlarm(SenderNotifications senderNotifications) {
        this.senderNotifications = senderNotifications;
        condition = new DeactivatedAlarm(this);
    }

    @Override
    public void handleEvent(SmartHomeHandler handler, Event event) {
        condition.handleEvent(handler, event);
    }

    public void changeState(Alarm condition) {
        this.condition = condition;
    }

    public SenderNotifications getSenderNotifications() {
        return senderNotifications;
    }
}
