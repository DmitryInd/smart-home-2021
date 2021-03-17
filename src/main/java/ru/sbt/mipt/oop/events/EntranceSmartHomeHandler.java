package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.command.*;
import ru.sbt.mipt.oop.smarthome.Door;
import ru.sbt.mipt.oop.smarthome.Light;
import ru.sbt.mipt.oop.smarthome.Room;
import ru.sbt.mipt.oop.smarthome.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class EntranceSmartHomeHandler implements SmartHomeHandler {
    private final SmartHome smartHome;

    public EntranceSmartHomeHandler(SmartHome smartHome) {
        this.smartHome = smartHome;

    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_CLOSED) return;
        Room room = findRoom(event.getObjectId());
        if (room != null && room.getName().equals("hall")) {
            // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
            // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
            turnOffAllLights();
        }
    }

    private Room findRoom(String id) {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(id)) {
                    return room;
                }
            }
        }

        return null;
    }

    private void turnOffAllLights() {
        for (Room homeRoom : smartHome.getRooms()) {
            for (Light light : homeRoom.getLights()) {
                light.setOn(false);
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                SenderCommands senderCommands = new DummySenderCommands();
                senderCommands.sendCommand(command);
            }
        }
    }
}
