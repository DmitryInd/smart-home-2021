package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.*;

import static ru.sbt.mipt.oop.events.EventType.*;

public class DeactivatedAlarm implements Alarm {
    private final BaseAlarm context;

    public DeactivatedAlarm(BaseAlarm context) {
        this.context = context;
    }

    @Override
    public void handleEvent(SmartHomeHandler handler, Event event) {
        if (event.getType() == ALARM_ACTIVATE) {
            context.changeState(new ActivatedAlarm(context,
                    ((AlarmEvent) event).getCode(), context.getSenderNotifications()));
        } else {
            handler.handleEvent(event);
        }
    }
}
