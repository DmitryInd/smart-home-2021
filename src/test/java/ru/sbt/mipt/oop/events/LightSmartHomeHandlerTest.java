package ru.sbt.mipt.oop.events;

import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.events.handler.LightSmartHomeHandler;
import ru.sbt.mipt.oop.events.receiver.SmartHomeReceiverEvents;
import ru.sbt.mipt.oop.events.event.SensorEvent;
import ru.sbt.mipt.oop.smarthome.SmartHomeAction;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.smarthome.*;
import ru.sbt.mipt.oop.smarthome.object.Door;
import ru.sbt.mipt.oop.smarthome.object.Light;
import ru.sbt.mipt.oop.smarthome.object.Room;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LightSmartHomeHandlerTest {
    @Test
    void switchExistingLightTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlersList = createDummyHandlers(smartHome);
        ReceiverEvents receiverEvents = new SmartHomeReceiverEvents(handlersList, new ConsoleOutputStream());
        EventsSource eventsSource = new EventsSource() {
            final Iterator<SensorEvent> events = Arrays.asList(
                    new SensorEvent(EventType.LIGHT_OFF, "1"),
                    new SensorEvent(EventType.LIGHT_ON, "2"),
                    new SensorEvent(EventType.LIGHT_OFF, "3")).iterator();

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
        CheckLightsAction checkLightsAction = new CheckLightsAction(condition);
        smartHome.execute(checkLightsAction);
    }

    @Test
    void switchNotExistingLightTest() {
        SmartHome smartHome = createDummyHome();
        List<SmartHomeHandler> handlersList = createDummyHandlers(smartHome);
        ReceiverEvents receiverEvents = new SmartHomeReceiverEvents(handlersList, new ConsoleOutputStream());
        EventsSource eventsSource = new EventsSource() {
            final Iterator<SensorEvent> events = Arrays.asList(
                    new SensorEvent(EventType.LIGHT_OFF, "0"),
                    new SensorEvent(EventType.LIGHT_ON, "h"),
                    new SensorEvent(EventType.LIGHT_OFF, "4")).iterator();

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
        CheckLightsAction checkLightsAction = new CheckLightsAction(condition);
        smartHome.execute(checkLightsAction);
    }

    private SmartHome createDummyHome() {
        Collection<Door> doors = new ArrayList<>();
        Collection<Light> lights1 = Arrays.asList(new Light("1", true), new Light("2", false));
        Collection<Light> lights2 = Arrays.asList(new Light("3", true));
        Collection<Room> rooms = Arrays.asList(new Room(lights1, doors, "first"),
                new Room(lights2, doors, "second"));

        return new SmartHome(rooms);
    }

    private List<SmartHomeHandler> createDummyHandlers(SmartHome smartHome) {
        return Arrays.asList(new LightSmartHomeHandler(smartHome, new ConsoleOutputStream()));
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