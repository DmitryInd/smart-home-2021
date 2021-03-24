package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.OpenCloseDoorSmartHomeAction;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.*;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class DoorSmartHomeHandler implements SmartHomeHandler {
    private final SmartHome smartHome;
    private final OutputStream output;

    public DoorSmartHomeHandler(SmartHome smartHome, OutputStream output) {
        this.smartHome = smartHome;
        this.output = output;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_OPEN && event.getType() != DOOR_CLOSED) return;
        // событие от двери
        smartHome.execute(new OpenCloseDoorSmartHomeAction(
                event.getType() == DOOR_OPEN, event.getObjectId(), output));
    }
}
