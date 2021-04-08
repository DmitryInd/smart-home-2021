package ru.sbt.mipt.oop.remote.command;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.remote.Command;

public class TriggerAlarm implements Command {
    private final Alarm alarm;

    TriggerAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.trigger();
    }
}