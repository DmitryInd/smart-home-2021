package ru.sbt.mipt.oop.events.alarm;

import ru.sbt.mipt.oop.events.SmartHomeHandler;

public interface AlarmSmartHomeHandler extends SmartHomeHandler{
    AlarmStatus getStatus();
}
