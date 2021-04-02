package ru.sbt.mipt.oop.alarm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlarmTest {
    @Test
    void checkStatusTest() {
        SmartHomeAlarm alarm = new SmartHomeAlarm();
        assertTrue(alarm.isDeactivated());

        alarm.activate("21343");
        assertFalse(alarm.isDeactivated());

        alarm.trigger();
        assertFalse(alarm.isDeactivated());
    }

    @Test
    void getActivatedStatus() {
        Alarm alarm = new SmartHomeAlarm();
        alarm.activate("897657");
        assertFalse(alarm.isDeactivated());

        alarm.deactivate("897657");
        alarm.activate("43439");
        assertFalse(alarm.isDeactivated());
        alarm.activate("41439");
        assertFalse(alarm.isDeactivated());
    }

    @Test
    void getDeactivatedStatus() {
        Alarm alarm = new SmartHomeAlarm();
        alarm.activate("897657");
        alarm.deactivate("897657");
        assertTrue(alarm.isDeactivated());

        alarm.trigger();
        assertTrue(alarm.isDeactivated());

        alarm.activate("43439");
        alarm.trigger();
        alarm.deactivate("43439");
        assertTrue(alarm.isDeactivated());

        alarm.activate("43439");
        alarm.activate("45459");
        alarm.deactivate("43439");
        assertTrue(alarm.isDeactivated());
    }

    @Test
    void getTriggeredStatus() {
        Alarm alarm = new SmartHomeAlarm();
        alarm.activate("897657");
        alarm.trigger();
        assertFalse(alarm.isDeactivated());
        alarm.activate("");
        assertFalse(alarm.isDeactivated());
        alarm.trigger();
        assertFalse(alarm.isDeactivated());

        alarm.deactivate("897657");
        alarm.activate("43439");
        alarm.deactivate("897657");
        assertFalse(alarm.isDeactivated());

        alarm.deactivate("43439");
        alarm.activate("43439");
        alarm.activate("45459");
        alarm.deactivate("45459");
        assertFalse(alarm.isDeactivated());
    }
}