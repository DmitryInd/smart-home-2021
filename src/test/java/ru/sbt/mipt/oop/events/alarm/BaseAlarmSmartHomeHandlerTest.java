package ru.sbt.mipt.oop.events.alarm;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.notification.SmsSenderNotifications;

import static org.junit.jupiter.api.Assertions.*;

class BaseAlarmSmartHomeHandlerTest {
    @Test
    void getActivatedStatus() {
        AlarmSmartHomeHandler alarmHandler = new BaseAlarmSmartHomeHandler(new SmsSenderNotifications());

        alarmHandler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        alarmHandler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        assertEquals(alarmHandler.getStatus(), AlarmStatus.ACTIVATED);
    }

    @Test
    void getDeactivatedStatus() {
        AlarmSmartHomeHandler alarmHandler = new BaseAlarmSmartHomeHandler(new SmsSenderNotifications());

        assertEquals(alarmHandler.getStatus(), AlarmStatus.DEACTIVATED);
        alarmHandler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        alarmHandler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        alarmHandler.handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2232"));
        alarmHandler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        assertEquals(alarmHandler.getStatus(), AlarmStatus.DEACTIVATED);
    }

    @Test
    void getTriggeredStatus() {
        AlarmSmartHomeHandler alarmHandler = new BaseAlarmSmartHomeHandler(new SmsSenderNotifications());
        alarmHandler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        alarmHandler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        alarmHandler.handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2532"));
        assertEquals(alarmHandler.getStatus(), AlarmStatus.TRIGGERED);
        alarmHandler.handleEvent(new SensorEvent(EventType.LIGHT_ON, "1"));
        assertEquals(alarmHandler.getStatus(), AlarmStatus.TRIGGERED);
        alarmHandler.handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2232"));
        alarmHandler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "2"));
        alarmHandler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        alarmHandler.handleEvent(new SensorEvent(EventType.LIGHT_ON, "3"));
        assertEquals(alarmHandler.getStatus(), AlarmStatus.TRIGGERED);
    }
}