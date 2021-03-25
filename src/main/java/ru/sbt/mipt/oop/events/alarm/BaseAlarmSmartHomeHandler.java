package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.notification.SenderNotifications;

public class BaseAlarmSmartHomeHandler implements AlarmSmartHomeHandler {
    private final SenderNotifications senderNotifications;
    private AlarmSmartHomeHandler condition;

    public BaseAlarmSmartHomeHandler(SenderNotifications senderNotifications) {
        this.senderNotifications = senderNotifications;
        condition = new DeactivatedAlarmSmartHomeHandler(this);
    }

    @Override
    public void handleEvent(Event event) {
        condition.handleEvent(event);
    }

    @Override
    public AlarmStatus getStatus() {
        return condition.getStatus();
    }

    public void changeState(AlarmSmartHomeHandler condition) {
        this.condition = condition;
    }

    public SenderNotifications getSenderNotifications() {
        return senderNotifications;
    }
}
