package ru.sbt.mipt.oop.smarthome.action;

import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.SmartHomeAction;
import ru.sbt.mipt.oop.smarthome.object.Light;
import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public class SwitchLightSmartHomeAction implements SmartHomeAction {
    private boolean isToOn;
    private final OutputStream output;
    private final String id;

    public SwitchLightSmartHomeAction(boolean isToOn, String id, OutputStream output) {
        this.isToOn = isToOn;
        this.id = id;
        this.output = output;
    }

    @Override
    public void performOn(SmartHomeObject object) {
        if (object instanceof Light) {
            Light light = (Light) object;
            if (!id.equals(light.getId())) return;
            light.setOn(isToOn);
            output.sendLog("Light " + light.getId() + " was " + (isToOn? "turned on.": "turned off."));
        }
    }
}
