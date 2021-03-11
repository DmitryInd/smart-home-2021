package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class SmartHomeReceiverEvents implements ReceiverEvents {
    public SmartHomeReceiverEvents(SmartHome smartHome, OutputStream output) {
        this.smartHome = smartHome;
        this.output = output;
    }

    @Override
    public void handleEvents(EventsSource eventsSource) {
        SensorEvent event = eventsSource.getNextSensorEvent();
        while (event != null) {
            output.sendLog("Got event: " + event);
            Handler handler = null;

            if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
                handler = new LightSmartHomeHandler(smartHome, output);
            } else if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
                handler = new DoorSmartHomeHandler(smartHome, output);
            }

            if (handler != null) {
                handler.handleEvent(event);
            }

            event = eventsSource.getNextSensorEvent();
        }
    }

    private SmartHome smartHome;
    private OutputStream output;
}
