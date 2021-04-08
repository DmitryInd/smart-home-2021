package ru.sbt.mipt.oop.events.handler;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.event.AlarmEvent;
import ru.sbt.mipt.oop.notification.SenderNotifications;

import static ru.sbt.mipt.oop.events.EventType.*;

public class ActivateAlarmSmartHomeHandler implements SmartHomeHandler {
    private final Alarm alarm;
    private final SenderNotifications senderNotifications;

    public ActivateAlarmSmartHomeHandler(Alarm alarm, SenderNotifications senderNotifications) {
        this.alarm = alarm;
        this.senderNotifications = senderNotifications;
    }

    @Override
    public void handleEvent(Event event) {
        if (!alarm.isDeactivated()) {
            alarm.trigger();
            senderNotifications.send("Intruder has been detected");
        } else if (event.getType() == ALARM_ACTIVATE) {
            alarm.activate(((AlarmEvent) event).getCode());
        }
    }
}
