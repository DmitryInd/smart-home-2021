package ru.sbt.mipt.oop.rc;

import rc.RemoteControl;
import ru.sbt.mipt.oop.rc.command.Command;

import java.util.Map;

public class SmartHomeRemoteControl implements RemoteControl {
    private final Map<String, Command> buttonCommands;
    private final RemoteControl nextRemoteControl;
    private final String rcId;

    public SmartHomeRemoteControl(Map<String, Command> buttonCommands, RemoteControl nextRemoteControl, String rcId) {
        this.buttonCommands = buttonCommands;
        this.nextRemoteControl = nextRemoteControl;
        this.rcId = rcId;
    }

    /**
     * This method will be called when a button buttonCode is pressed on a remote control with id rcId
     *
     * @param buttonCode button letter: “A”, “B”, “C”, “D”, “1”, “2”, “3”, “4”
     * @param rcId       remote control id
     */
    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        if (rcId.equals(this.rcId)) {
            Command toExecution = buttonCommands.get(buttonCode);
            if (toExecution != null) {
                toExecution.execute();
            }
        } else {
            nextRemoteControl.onButtonPressed(buttonCode, rcId);
        }
    }
}
