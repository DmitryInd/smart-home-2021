package ru.sbt.mipt.oop.alarm;

public class ActivatedAlarmState implements AlarmState {
    private final Alarm context;
    private final String code;

    public ActivatedAlarmState(Alarm context, String code) {
        this.context = context;
        this.code = code;
    }

    @Override
    public AlarmState deactivate(String code) {
        if (this.code.equals(code)) {
            return new DeactivatedAlarmState(context);
        } else {
            return trigger();
        }
    }

    @Override
    public AlarmState activate(String code) {
        return this;
    }

    @Override
    public AlarmState trigger() {
        return new TriggeredAlarmState(context, code);
    }
}
