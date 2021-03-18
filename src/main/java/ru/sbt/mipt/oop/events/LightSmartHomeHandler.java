package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.SwitchLightSmartHomeAction;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class LightSmartHomeHandler implements SmartHomeHandler {
    private final SmartHome smartHome;
    private final OutputStream output;

    public LightSmartHomeHandler(SmartHome smartHome, OutputStream output) {
        this.smartHome = smartHome;
        this.output = output;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != LIGHT_ON && event.getType() != LIGHT_OFF) return;
        // событие от источника света
        smartHome.execute(new SwitchLightSmartHomeAction(
                event.getType() == LIGHT_ON, event.getObjectId(), output));
    }

}
