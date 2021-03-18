package ru.sbt.mipt.oop.events;

import org.junit.jupiter.api.*;

import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.smarthome.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DoorSmartHomeHandlerTest {

    @Test
    void moveExistingDoorTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlersList = createDummyHandlers(smartHome);
        ReceiverEvents receiverEvents = new SmartHomeReceiverEvents(handlersList, new ConsoleOutputStream());
        EventsSource eventsSource = new EventsSource() {
            final Iterator<SensorEvent> events = Arrays.asList(
                    new SensorEvent(SensorEventType.DOOR_OPEN, "1"),
                    new SensorEvent(SensorEventType.DOOR_CLOSED, "2"),
                    new SensorEvent(SensorEventType.DOOR_OPEN, "3")).iterator();

            @Override
            public SensorEvent getNextSensorEvent() {
                return events.hasNext()? events.next(): null;
            }
        };
        receiverEvents.handleEvents(eventsSource);

        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", true);
        condition.put("2", false);
        condition.put("3", true);
        CheckDoorsAction checkDoorsAction = new CheckDoorsAction(condition);
        smartHome.execute(checkDoorsAction);
    }

    @Test
    void moveNotExistingDoorTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlersList = createDummyHandlers(smartHome);
        ReceiverEvents receiverEvents = new SmartHomeReceiverEvents(handlersList, new ConsoleOutputStream());
        EventsSource eventsSource = new EventsSource() {
            final Iterator<SensorEvent> events = Arrays.asList(
                    new SensorEvent(SensorEventType.DOOR_OPEN, "0"),
                    new SensorEvent(SensorEventType.DOOR_CLOSED, "4"),
                    new SensorEvent(SensorEventType.DOOR_OPEN, "5")).iterator();

            @Override
            public SensorEvent getNextSensorEvent() {
                return events.hasNext()? events.next(): null;
            }
        };

        receiverEvents.handleEvents(eventsSource);
        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", false);
        condition.put("2", true);
        condition.put("3", false);
        CheckDoorsAction checkDoorsAction = new CheckDoorsAction(condition);
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

    private List<SmartHomeHandler> createDummyHandlers(SmartHome smartHome) {
        return Arrays.asList(new DoorSmartHomeHandler(smartHome, new ConsoleOutputStream()));
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