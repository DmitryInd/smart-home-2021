package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.GetDoorRoomSmartHomeAction;
import ru.sbt.mipt.oop.actions.TurnOffAllLightsSmartHomeAction;
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
        StringBuilder roomName = new StringBuilder();
        smartHome.execute(new GetDoorRoomSmartHomeAction(roomName, event.getObjectId()));
        if (roomName.toString().equals("hall")) {
            // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
            // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
            smartHome.execute(new TurnOffAllLightsSmartHomeAction());
        }
    }
}
