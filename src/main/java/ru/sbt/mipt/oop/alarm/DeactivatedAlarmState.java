package ru.sbt.mipt.oop.alarm;


public class DeactivatedAlarmState implements AlarmState {
    private final Alarm context;

    public DeactivatedAlarmState(Alarm context) {
        this.context = context;
    }

    @Override
    public AlarmState deactivate(String code) {
        return this;
    }

    @Override
    public AlarmState activate(String code) {
        return new ActivatedAlarmState(context, code);
    }

    @Override
    public AlarmState trigger() {
        return this;
    }
}
