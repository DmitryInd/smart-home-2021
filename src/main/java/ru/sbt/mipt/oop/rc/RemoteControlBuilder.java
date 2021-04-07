package ru.sbt.mipt.oop.rc;

import ru.sbt.mipt.oop.rc.command.Command;

public interface RemoteControlBuilder {
    void reset();

    void setRcId(String rcId);
    void setEventForButton(String id, Command command);
}
