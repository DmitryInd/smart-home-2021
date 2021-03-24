package ru.sbt.mipt.oop.events;

import org.junit.jupiter.api.*;

import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.command.DummySenderCommands;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.smarthome.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EntranceSmartHomeHandlerTest {

    @Test
    void exitFromHallTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlersList = createDummyHandlers(smartHome);
        ReceiverEvents receiverEvents = new SmartHomeReceiverEvents(handlersList, new ConsoleOutputStream());
        EventsSource eventsSource = new EventsSource() {
            final Iterator<SensorEvent> events = Arrays.asList(
                    new SensorEvent(EventType.DOOR_OPEN, "3"),
                    new SensorEvent(EventType.DOOR_CLOSED, "1")).iterator();

            @Override
            public SensorEvent getNextSensorEvent() {
                return events.hasNext()? events.next(): null;
            }
        };
        receiverEvents.handleEvents(eventsSource);

        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", false);
        condition.put("2", false);
        condition.put("3", false);
        condition.put("4", false);
        CheckLightsAction checkLightsAction = new CheckLightsAction(condition);
        smartHome.execute(checkLightsAction);
    }

    @Test
    void notExitFromHallTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlersList = createDummyHandlers(smartHome);
        ReceiverEvents receiverEvents = new SmartHomeReceiverEvents(handlersList, new ConsoleOutputStream());
        EventsSource eventsSource = new EventsSource() {
            final Iterator<SensorEvent> events = Arrays.asList(
                    new SensorEvent(EventType.DOOR_OPEN, "1"),
                    new SensorEvent(EventType.DOOR_CLOSED, "3"),
                    new SensorEvent(EventType.DOOR_OPEN, "4")).iterator();

            @Override
            public SensorEvent getNextSensorEvent() {
                return events.hasNext()? events.next(): null;
            }
        };

        receiverEvents.handleEvents(eventsSource);
        HashMap<String, Boolean> condition = new HashMap<>();
        condition.put("1", true);
        condition.put("2", true);
        condition.put("3", true);
        condition.put("4", true);
        CheckLightsAction checkLightsAction = new CheckLightsAction(condition);
        smartHome.execute(checkLightsAction);
    }

    private SmartHome createDummyHome() {
        Collection<Door> doors1 = Arrays.asList(new Door(true, "1"));
        Collection<Door> doors2 = Arrays.asList(new Door(false, "3"));
        Collection<Light> lights1 = Arrays.asList(new Light("1", true), new Light("2", true));
        Collection<Light> lights2 = Arrays.asList(new Light("3", true), new Light("4", true));
        Collection<Room> rooms = Arrays.asList(new Room(lights1, doors1, "hall"),
                new Room(lights2, doors2, "notHall"));

        return new SmartHome(rooms);
    }

    private List<SmartHomeHandler> createDummyHandlers(SmartHome smartHome) {
        return Arrays.asList(new EntranceSmartHomeHandler(smartHome, new DummySenderCommands()));
    }

    private class CheckLightsAction implements SmartHomeAction {
        Map<String, Boolean> condition;
        CheckLightsAction(Map<String, Boolean> condition) {
            this.condition = condition;
        }

        @Override
        public void performOn(SmartHomeObject object) {
            if (object instanceof Light) {
                Light lights = (Light) object;
                assertEquals(condition.get(lights.getId()), lights.isOn());
            }
        }
    }
}