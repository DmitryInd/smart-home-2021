package ru.sbt.mipt.oop.smarthome.object;

public interface SmartHomeRecorder {
    SmartHome readSmartHome();
    void saveSmartHome(SmartHome smartHome);
}
