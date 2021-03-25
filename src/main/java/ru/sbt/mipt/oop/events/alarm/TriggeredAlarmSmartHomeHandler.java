package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.notification.SenderNotifications;

import static ru.sbt.mipt.oop.events.EventType.*;
import static ru.sbt.mipt.oop.events.alarm.AlarmStatus.*;

public class TriggeredAlarmSmartHomeHandler implements AlarmSmartHomeHandler {
    private final BaseAlarmSmartHomeHandler context;
    private final String code;
    private final SenderNotifications senderNotifications;

    public TriggeredAlarmSmartHomeHandler(BaseAlarmSmartHomeHandler context, String code,
                                          SenderNotifications senderNotifications) {
        this.context = context;
        this.code = code;
        this.senderNotifications = senderNotifications;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getType() == ALARM_DEACTIVATE && ((AlarmEvent) event).getCode().equals(code)) {
            context.changeState(new DeactivatedAlarmSmartHomeHandler(context));
        } else {
            senderNotifications.send("Intruder is trying to use the smart home");
        }
    }

    @Override
    public AlarmStatus getStatus() {
        return TRIGGERED;
    }
}
