package ru.sbt.mipt.oop.events.alarm;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.events.DoorSmartHomeHandler;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.notification.SmsSenderNotifications;
import ru.sbt.mipt.oop.smarthome.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorWithAlarmSmartHomeHandlerTest {
    @Test
    void activateEventTest() {
        SmartHome smartHome = createDummyHome();
        SmartHomeHandler handler = createDummyHandler(smartHome);
        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", true);
        condition.put("2", false);
        condition.put("3", false);
        CheckDoorsAction checkDoorsAction;
        checkDoorsAction = new CheckDoorsAction(condition);

        handler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "2"));
        smartHome.execute(checkDoorsAction);

        handler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        smartHome.execute(checkDoorsAction);
    }

    @Test
    void triggerEventTest() {
        SmartHome smartHome = createDummyHome();
        SmartHomeHandler handler = createDummyHandler(smartHome);
        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", true);
        condition.put("2", true);
        condition.put("3", false);
        CheckDoorsAction checkDoorsAction;
        checkDoorsAction = new CheckDoorsAction(condition);

        handler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        smartHome.execute(checkDoorsAction);

        handler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "2"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "3"));
        smartHome.execute(checkDoorsAction);
    }

    @Test
    void rightDeactivateEventTest() {
        SmartHome smartHome = createDummyHome();
        SmartHomeHandler handler = createDummyHandler(smartHome);
        HashMap<String, Boolean> condition = new HashMap<>();
        CheckDoorsAction checkDoorsAction;

        handler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        condition.put("1", true);
        condition.put("2", true);
        condition.put("3", false);
        checkDoorsAction = new CheckDoorsAction(condition);
        smartHome.execute(checkDoorsAction);

        handler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handler.handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2232"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "3"));
        condition.put("1", false);
        condition.put("2", true);
        condition.put("3", true);
        checkDoorsAction = new CheckDoorsAction(condition);
        smartHome.execute(checkDoorsAction);

        handler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        handler.handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2232"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "3"));
        condition.put("1", false);
        condition.put("2", true);
        condition.put("3", false);
        checkDoorsAction = new CheckDoorsAction(condition);
        smartHome.execute(checkDoorsAction);
    }

    @Test
    void falseDeactivateEventTest() {
        SmartHome smartHome = createDummyHome();
        SmartHomeHandler handler = createDummyHandler(smartHome);
        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", true);
        condition.put("2", true);
        condition.put("3", false);
        CheckDoorsAction checkDoorsAction;
        checkDoorsAction = new CheckDoorsAction(condition);

        handler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        smartHome.execute(checkDoorsAction);

        handler.handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handler.handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2532"));
        smartHome.execute(checkDoorsAction);
        handler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "2"));
        handler.handleEvent(new SensorEvent(EventType.DOOR_OPEN, "3"));
        smartHome.execute(checkDoorsAction);
        handler.handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2532"));
        smartHome.execute(checkDoorsAction);
    }

    private SmartHome createDummyHome() {
        Collection<Door> doors1 = Arrays.asList(new Door(false, "1"), new Door(true, "2"));
        Collection<Door> doors2 = Arrays.asList(new Door(false, "3"));
        Collection<Light> lights = new ArrayList<>();
        Collection<Room> rooms = Arrays.asList(new Room(lights, doors1, "first"),
                new Room(lights, doors2, "second"));

        return new SmartHome(rooms);
    }

    private SmartHomeHandler createDummyHandler(SmartHome smartHome) {
        AlarmSmartHomeHandler alarmSmartHomeHandler = new BaseAlarmSmartHomeHandler(new SmsSenderNotifications());
        return new DecoratorWithAlarmSmartHomeHandler(
                new DoorSmartHomeHandler(smartHome, new ConsoleOutputStream()), alarmSmartHomeHandler);
    }

    private class CheckDoorsAction implements SmartHomeAction {
        Map<String, Boolean> condition;
        CheckDoorsAction(Map<String, Boolean> condition) {
            this.condition = condition;
        }

        @Override
        public void performOn(SmartHomeObject object) {
            if (object instanceof Door) {
                Door door = (Door) object;
                assertEquals(condition.get(door.getId()), door.isOpen());
            }
        }
    }
}