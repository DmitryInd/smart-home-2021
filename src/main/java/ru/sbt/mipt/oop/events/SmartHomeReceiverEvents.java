package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.log.OutputStream;

import java.util.List;

public class SmartHomeReceiverEvents implements ReceiverEvents {
    private final List<SmartHomeHandler> handlersList;
    private final OutputStream output;

    public SmartHomeReceiverEvents(List<SmartHomeHandler> handlersList, OutputStream output) {
        this.handlersList = handlersList;
        this.output = output;
    }

    @Override
    public void handleEvents(EventsSource eventsSource) {
        Event event = eventsSource.getNextSensorEvent();
        while (event != null) {
            output.sendLog("Got event: " + event);
            for (Handler handler: handlersList) {
                handler.handleEvent(event);
            }

            event = eventsSource.getNextSensorEvent();
        }
    }
}
