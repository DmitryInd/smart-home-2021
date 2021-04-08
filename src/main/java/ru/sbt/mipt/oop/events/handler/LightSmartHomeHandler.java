package ru.sbt.mipt.oop.events.handler;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.events.event.SensorEvent;
import ru.sbt.mipt.oop.smarthome.action.SwitchLightSmartHomeAction;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;

import static ru.sbt.mipt.oop.events.EventType.*;

public class LightSmartHomeHandler implements SmartHomeHandler {
    private final SmartHome smartHome;
    private final OutputStream output;

    public LightSmartHomeHandler(SmartHome smartHome, OutputStream output) {
        this.smartHome = smartHome;
        this.output = output;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getType() != LIGHT_ON && event.getType() != LIGHT_OFF) return;
        // событие от источника света
        smartHome.execute(new SwitchLightSmartHomeAction(
                event.getType() == LIGHT_ON, ((SensorEvent) event).getObjectId(), output));
    }

}
