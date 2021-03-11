package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.Room;
import ru.sbt.mipt.oop.smarthome.Light;
import ru.sbt.mipt.oop.smarthome.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_ON;

public class LightSmartHomeHandler extends SmartHomeHandler {
    public LightSmartHomeHandler(SmartHome smartHome, OutputStream output) {
        super(smartHome, output);
    }

    @Override
    public void handleEvent(SensorEvent event) {
        // событие от источника света
        Light light = findLight(event);
        if (light != null) {
            switchLight(light, event.getType() == LIGHT_ON);
        }
    }

    private Room currentRoom = null;

    private Light findLight(SensorEvent event) {
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                if (light.getId().equals(event.getObjectId())) {
                    currentRoom = room;
                    return light;
                }
            }
        }

        return null;
    }

    private void switchLight(Light light, boolean isOn) {
        light.setOn(isOn);
        output.sendLog("Light " + light.getId() + " in room " + currentRoom.getName() + " was " +
                (isOn? "turned on.": "turned off."));
    }
}
