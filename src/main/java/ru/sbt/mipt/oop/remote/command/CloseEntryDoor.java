package ru.sbt.mipt.oop.remote.command;

import ru.sbt.mipt.oop.smarthome.action.OpenCloseDoorSmartHomeAction;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.remote.Command;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;

public class CloseEntryDoor implements Command {
    private final SmartHome smartHome;
    private final String entryDoorId;
    private final OutputStream outputStream;

    CloseEntryDoor(SmartHome smartHome, String entryDoorId, OutputStream outputStream) {
        this.smartHome = smartHome;
        this.entryDoorId = entryDoorId;
        this.outputStream = outputStream;
    }

    @Override
    public void execute() {
        smartHome.execute(new OpenCloseDoorSmartHomeAction(false, entryDoorId, outputStream));
    }
}