package ru.sbt.mipt.oop.events.handler;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.events.event.SensorEvent;
import ru.sbt.mipt.oop.smarthome.action.OpenCloseDoorSmartHomeAction;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;

import static ru.sbt.mipt.oop.events.EventType.*;

public class DoorSmartHomeHandler implements SmartHomeHandler {
    private final SmartHome smartHome;
    private final OutputStream output;

    public DoorSmartHomeHandler(SmartHome smartHome, OutputStream output) {
        this.smartHome = smartHome;
        this.output = output;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getType() != DOOR_OPEN && event.getType() != DOOR_CLOSED) return;
        // событие от двери
        smartHome.execute(new OpenCloseDoorSmartHomeAction(
                event.getType() == DOOR_OPEN, ((SensorEvent) event).getObjectId(), output));
    }
}
