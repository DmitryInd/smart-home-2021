package ru.sbt.mipt.oop.events.handler;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.events.event.SensorEvent;
import ru.sbt.mipt.oop.smarthome.action.CheckRoomOfDoorSmartHomeAction;
import ru.sbt.mipt.oop.smarthome.action.TurnOffAllLightsSmartHomeAction;
import ru.sbt.mipt.oop.command.SenderCommands;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;


import static ru.sbt.mipt.oop.events.EventType.DOOR_CLOSED;

public class EntranceSmartHomeHandler implements SmartHomeHandler {
    private final SmartHome smartHome;
    private final SenderCommands senderCommands;

    public EntranceSmartHomeHandler(SmartHome smartHome, SenderCommands senderCommands) {
        this.smartHome = smartHome;
        this.senderCommands = senderCommands;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getType() != DOOR_CLOSED) return;
        // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
        // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
        smartHome.execute(new CheckRoomOfDoorSmartHomeAction(((SensorEvent) event).getObjectId(),
                "hall",
                new TurnOffAllLightsSmartHomeAction(senderCommands)));
    }
}
