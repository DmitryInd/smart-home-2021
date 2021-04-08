package ru.sbt.mipt.oop.remote;

public interface RemoteControlBuilder {
    void reset();

    void setRcId(String rcId);
    void setEventForButton(String id, Command command);
}
