package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.notification.SenderNotifications;

import static ru.sbt.mipt.oop.events.EventType.*;

public class ActivatedAlarm implements Alarm {
    private final BaseAlarm context;
    private final String code;
    private final SenderNotifications senderNotifications;

    public ActivatedAlarm(BaseAlarm context, String code,
                          SenderNotifications senderNotifications) {
        this.context = context;
        this.code = code;
        this.senderNotifications = senderNotifications;
    }

    @Override
    public void handleEvent(SmartHomeHandler handler, Event event) {
        if (event.getType() == ALARM_DEACTIVATE && ((AlarmEvent) event).getCode().equals(code)) {
            context.changeState(new DeactivatedAlarm(context));
        } else {
            senderNotifications.send("Sending sms");
            context.changeState(new TriggeredAlarm(context, code, senderNotifications));
        }
    }
}
