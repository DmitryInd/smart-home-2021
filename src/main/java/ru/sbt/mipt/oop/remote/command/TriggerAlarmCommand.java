package ru.sbt.mipt.oop.remote.command;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.remote.Command;

public class TriggerAlarmCommand implements Command {
    private final Alarm alarm;

    public TriggerAlarmCommand(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.trigger();
    }
}