package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.*;

import static ru.sbt.mipt.oop.events.EventType.*;
import static ru.sbt.mipt.oop.events.alarm.AlarmStatus.*;

public class DeactivatedAlarmSmartHomeHandler implements AlarmSmartHomeHandler {
    private final BaseAlarmSmartHomeHandler context;

    public DeactivatedAlarmSmartHomeHandler(BaseAlarmSmartHomeHandler context) {
        this.context = context;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getType() == ALARM_ACTIVATE) {
            context.changeState(new ActivatedAlarmSmartHomeHandler(context,
                    ((AlarmEvent) event).getCode(), context.getSenderNotifications()));
        }
    }

    @Override
    public AlarmStatus getStatus() {
        return DEACTIVATED;
    }
}
