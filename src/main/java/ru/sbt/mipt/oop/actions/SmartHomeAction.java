package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.smarthome.SmartHomeObject;

public interface SmartHomeAction {
    void performOn(SmartHomeObject object);
}
