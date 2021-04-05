package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.notification.SenderNotifications;

import static ru.sbt.mipt.oop.events.EventType.*;

public class DeactivateSmartHomeHandler implements SmartHomeHandler {
    private final Alarm alarm;
    private final SenderNotifications senderNotifications;

    public DeactivateSmartHomeHandler(Alarm alarm, SenderNotifications senderNotifications) {
        this.alarm = alarm;
        this.senderNotifications = senderNotifications;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getType() == ALARM_DEACTIVATE) {
            alarm.deactivate(((AlarmEvent) event).getCode());
        }
        if (!alarm.isDeactivated()) {
            alarm.trigger();
            senderNotifications.send("Intruder has been detected");
        }
    }
}
