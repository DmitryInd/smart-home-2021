package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.notification.SenderNotifications;

import static ru.sbt.mipt.oop.events.EventType.ALARM_ACTIVATE;

public class ActivatedAlarmSmartHomeHandler implements AlarmSmartHomeHandler {
    private final BaseAlarmSmartHomeHandler context;
    private final String code;
    private final SenderNotifications senderNotifications;

    public ActivatedAlarmSmartHomeHandler(BaseAlarmSmartHomeHandler context, String code,
                                          SenderNotifications senderNotifications) {
        this.context = context;
        this.code = code;
        this.senderNotifications = senderNotifications;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getType() == ALARM_ACTIVATE && ((AlarmEvent) event).getCode().equals(code)) {
            context.changeState(new DeactivatedAlarmSmartHomeHandler(context));
        } else {
            senderNotifications.send("Sending sms");
            context.changeState(new TriggeredAlarmSmartHomeHandler(context, code, senderNotifications));
        }
    }
}
