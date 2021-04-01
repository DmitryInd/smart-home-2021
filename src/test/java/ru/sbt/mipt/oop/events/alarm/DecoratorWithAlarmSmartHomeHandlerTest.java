package ru.sbt.mipt.oop.events.alarm;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.alarm.*;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.notification.SenderNotifications;
import ru.sbt.mipt.oop.notification.SmsSenderNotifications;
import ru.sbt.mipt.oop.smarthome.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorWithAlarmSmartHomeHandlerTest {
    @Test
    void activateEventTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlers = createDummyHandler(smartHome);
        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", true);
        condition.put("2", false);
        condition.put("3", false);
        CheckDoorsAction checkDoorsAction;
        checkDoorsAction = new CheckDoorsAction(condition);

        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "2"));
        smartHome.execute(checkDoorsAction);

        handlers.get(2).handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        smartHome.execute(checkDoorsAction);
    }

    @Test
    void triggerEventTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlers = createDummyHandler(smartHome);
        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", true);
        condition.put("2", true);
        condition.put("3", false);
        CheckDoorsAction checkDoorsAction;
        checkDoorsAction = new CheckDoorsAction(condition);

        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        smartHome.execute(checkDoorsAction);

        handlers.get(2).handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "2"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_OPEN, "3"));
        smartHome.execute(checkDoorsAction);
    }

    @Test
    void rightDeactivateEventTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlers = createDummyHandler(smartHome);
        HashMap<String, Boolean> condition = new HashMap<>();
        CheckDoorsAction checkDoorsAction;

        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        condition.put("1", true);
        condition.put("2", true);
        condition.put("3", false);
        checkDoorsAction = new CheckDoorsAction(condition);
        smartHome.execute(checkDoorsAction);

        handlers.get(2).handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handlers.get(1).handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2232"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_OPEN, "3"));
        condition.put("1", false);
        condition.put("2", true);
        condition.put("3", true);
        checkDoorsAction = new CheckDoorsAction(condition);
        smartHome.execute(checkDoorsAction);

        handlers.get(2).handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        handlers.get(1).handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2232"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "3"));
        condition.put("1", false);
        condition.put("2", true);
        condition.put("3", false);
        checkDoorsAction = new CheckDoorsAction(condition);
        smartHome.execute(checkDoorsAction);
    }

    @Test
    void falseDeactivateEventTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlers = createDummyHandler(smartHome);
        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", true);
        condition.put("2", true);
        condition.put("3", false);
        CheckDoorsAction checkDoorsAction;
        checkDoorsAction = new CheckDoorsAction(condition);

        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_OPEN, "1"));
        smartHome.execute(checkDoorsAction);

        handlers.get(2).handleEvent(new AlarmEvent(EventType.ALARM_ACTIVATE, "2232"));
        handlers.get(1).handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2532"));
        smartHome.execute(checkDoorsAction);
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "1"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_CLOSED, "2"));
        handlers.get(0).handleEvent(new SensorEvent(EventType.DOOR_OPEN, "3"));
        smartHome.execute(checkDoorsAction);
        handlers.get(1).handleEvent(new AlarmEvent(EventType.ALARM_DEACTIVATE, "2532"));
        smartHome.execute(checkDoorsAction);
    }

    private SmartHome createDummyHome() {
        Collection<Door> doors1 = Arrays.asList(new Door(false, "1"), new Door(true, "2"));
        Collection<Door> doors2 = Collections.singletonList(new Door(false, "3"));
        Collection<Light> lights = new ArrayList<>();
        Collection<Room> rooms = Arrays.asList(new Room(lights, doors1, "first"),
                new Room(lights, doors2, "second"));

        return new SmartHome(rooms);
    }

    private List<SmartHomeHandler> createDummyHandler(SmartHome smartHome) {
        Alarm alarm = new BaseAlarm();
        SenderNotifications senderNotifications = new SmsSenderNotifications();
        return Arrays.asList(
                new DecoratorWithAlarmSmartHomeHandler(
                        senderNotifications, new DoorSmartHomeHandler(smartHome, new ConsoleOutputStream()), alarm),
                new DecoratorWithAlarmSmartHomeHandler(
                        senderNotifications, new DeactivateSmartHomeHandler(alarm, new SmsSenderNotifications()), alarm),
                new DecoratorWithAlarmSmartHomeHandler(
                        senderNotifications, new ActivateAlarmSmartHomeHandler(alarm, new SmsSenderNotifications()), alarm)
        );
    }

    private static class CheckDoorsAction implements SmartHomeAction {
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