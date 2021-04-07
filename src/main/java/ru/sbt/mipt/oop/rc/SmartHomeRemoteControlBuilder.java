package ru.sbt.mipt.oop.rc;

import ru.sbt.mipt.oop.rc.command.Command;

import java.util.HashMap;
import java.util.Map;

public class SmartHomeRemoteControlBuilder implements RemoteControlBuilder {
    private SmartHomeRemoteControl prevRemoteControl;
    private final Map<String, Command> buttonCommands = new HashMap<>();
    private String rcId;

    public SmartHomeRemoteControlBuilder() {}

    public SmartHomeRemoteControlBuilder(SmartHomeRemoteControl prevRemoteControl) {
        this.prevRemoteControl = prevRemoteControl;
    }

    @Override
    public void reset() {
        buttonCommands.clear();
    }

    @Override
    public void setRcId(String rcId) {
        this.rcId = rcId;
    }

    @Override
    public void setEventForButton(String id, Command command) {
        buttonCommands.put(id, command);
    }

    public SmartHomeRemoteControl getResult() {
        prevRemoteControl = new SmartHomeRemoteControl(buttonCommands, prevRemoteControl, rcId);
        reset();
        return prevRemoteControl;
    }
}
