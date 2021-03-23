package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.Door;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public class OpenCloseDoorSmartHomeAction implements SmartHomeAction {
    private final boolean isToOpen;
    private final OutputStream output;
    private final String id;

    public OpenCloseDoorSmartHomeAction(boolean isToOpen, String id, OutputStream output) {
        this.isToOpen = isToOpen;
        this.id = id;
        this.output = output;
    }

    @Override
    public void performOn(SmartHomeObject object) {
        if (object instanceof Door) {
            Door door = (Door) object;
            if (!id.equals(door.getId())) return;
            door.setOpen(isToOpen);
            output.sendLog("Door " + door.getId() + " was " + (isToOpen? "opened.": "closed."));
        }
    }
}
