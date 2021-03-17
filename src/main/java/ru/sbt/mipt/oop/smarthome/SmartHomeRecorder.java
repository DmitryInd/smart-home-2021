package ru.sbt.mipt.oop.smarthome;

public interface SmartHomeRecorder {
    SmartHome readSmartHome();
    void saveSmartHome(SmartHome smartHome);
}
