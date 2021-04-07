package ru.sbt.mipt.oop.rc.command;

import ru.sbt.mipt.oop.alarm.Alarm;

public class ActivateAlarm implements Command {
    private final Alarm alarm;
    private final String code;

    ActivateAlarm(Alarm alarm, String code) {
        this.alarm = alarm;
        this.code = code;
    }

    @Override
    public void execute() {
        alarm.activate(code);
    }
}