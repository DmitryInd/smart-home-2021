package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.notification.SenderNotifications;

import static ru.sbt.mipt.oop.events.EventType.*;

public class TriggeredAlarm implements Alarm {
    private final BaseAlarm context;
    private final String code;
    private final SenderNotifications senderNotifications;

    public TriggeredAlarm(BaseAlarm context, String code,
                          SenderNotifications senderNotifications) {
        this.context = context;
        this.code = code;
        this.senderNotifications = senderNotifications;
    }

    @Override
    public void handleEvent(SmartHomeHandler handler, Event event) {
        senderNotifications.send("Sending sms");
        if (event.getType() == ALARM_DEACTIVATE && ((AlarmEvent) event).getCode().equals(code)) {
            context.changeState(new DeactivatedAlarm(context));
        }
    }
}
