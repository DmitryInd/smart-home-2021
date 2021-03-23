package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.actions.GetDoorRoomSmartHomeAction;
import ru.sbt.mipt.oop.actions.TurnOffAllLightsSmartHomeAction;
import ru.sbt.mipt.oop.command.SenderCommands;
import ru.sbt.mipt.oop.pointer.Pointer;
import ru.sbt.mipt.oop.smarthome.SmartHome;


import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class EntranceSmartHomeHandler implements SmartHomeHandler {
    private final SmartHome smartHome;
    private final SenderCommands senderCommands;

    public EntranceSmartHomeHandler(SmartHome smartHome, SenderCommands senderCommands) {
        this.smartHome = smartHome;
        this.senderCommands = senderCommands;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_CLOSED) return;

        Pointer<String> roomName = new Pointer<>("");
        smartHome.execute(new GetDoorRoomSmartHomeAction(roomName, event.getObjectId()));
        if (roomName.getObj().equals("hall")) {
            // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
            // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
            smartHome.execute(new TurnOffAllLightsSmartHomeAction(senderCommands));
        }
    }
}
