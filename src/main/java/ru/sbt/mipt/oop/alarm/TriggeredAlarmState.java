package ru.sbt.mipt.oop.alarm;

public class TriggeredAlarmState implements AlarmState {
    private final Alarm context;
    private String code;

    public TriggeredAlarmState(Alarm context, String code) {
        this.context = context;
        this.code = code;
    }

    @Override
    public AlarmState deactivate(String code) {
        if (this.code.equals(code)) {
            return new DeactivatedAlarmState(context);
        } else {
            return this;
        }
    }

    @Override
    public AlarmState activate(String code) {
        return this;
    }

    @Override
    public AlarmState trigger() {
        return this;
    }
}
