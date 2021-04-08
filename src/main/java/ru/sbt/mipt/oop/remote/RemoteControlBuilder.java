package ru.sbt.mipt.oop.remote;

public interface RemoteControlBuilder<T> {
    void reset();

    void setRcId(String rcId);
    void setEventForButton(String id, Command command);
    T getResult();
}
