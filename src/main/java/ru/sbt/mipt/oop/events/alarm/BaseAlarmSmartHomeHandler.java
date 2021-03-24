package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.notification.SenderNotifications;

import java.util.List;

public class BaseAlarmSmartHomeHandler implements AlarmSmartHomeHandler {
    private final List<SmartHomeHandler> handlersList;
    private final SenderNotifications senderNotifications;
    private AlarmSmartHomeHandler condition;

    public BaseAlarmSmartHomeHandler(List<SmartHomeHandler> handlersList, SenderNotifications senderNotifications) {
        this.handlersList = handlersList;
        this.senderNotifications = senderNotifications;
        condition = new DeactivatedAlarmSmartHomeHandler(this);
    }

    @Override
    public void handleEvent(Event event) {
        condition.handleEvent(event);
    }

    public void changeState(AlarmSmartHomeHandler condition) {
        this.condition = condition;
    }
    public List<SmartHomeHandler> getHandlersList() {
        return handlersList;
    }

    public SenderNotifications getSenderNotifications() {
        return senderNotifications;
    }
}
