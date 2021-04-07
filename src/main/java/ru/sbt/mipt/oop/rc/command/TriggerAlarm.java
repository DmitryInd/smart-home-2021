package ru.sbt.mipt.oop.rc.command;

import ru.sbt.mipt.oop.alarm.Alarm;

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