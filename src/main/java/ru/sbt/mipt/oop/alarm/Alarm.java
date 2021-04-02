package ru.sbt.mipt.oop.alarm;

public interface Alarm {
    void deactivate(String code);
    void activate(String code);
    void trigger();

    boolean isDeactivated();
}
