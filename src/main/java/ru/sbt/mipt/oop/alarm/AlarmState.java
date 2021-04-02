package ru.sbt.mipt.oop.alarm;

public interface AlarmState {
    AlarmState deactivate(String code);
    AlarmState activate(String code);
    AlarmState trigger();
}
