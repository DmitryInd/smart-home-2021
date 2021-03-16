package ru.sbt.mipt.oop.events;

import javafx.util.Pair;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.Room;
import ru.sbt.mipt.oop.smarthome.Light;
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
        if (event.getType() != DOOR_OPEN && event.getType() != DOOR_CLOSED) return;
        // событие от источника света
        Pair<Light, Room> targetPlace = findLight(event);
        if (targetPlace != null) {
            switchLight(targetPlace.getKey(), targetPlace.getValue(), event.getType() == LIGHT_ON);
        }
    }

    private Pair<Light, Room> findLight(SensorEvent event) {
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                if (light.getId().equals(event.getObjectId())) {
                    return new Pair<>(light, room);
                }
            }
        }

        return null;
    }

    private void switchLight(Light light, Room room, boolean isOn) {
        light.setOn(isOn);
        output.sendLog("Light " + light.getId() + " in room " + room.getName() + " was " +
                (isOn? "turned on.": "turned off."));
    }
}
