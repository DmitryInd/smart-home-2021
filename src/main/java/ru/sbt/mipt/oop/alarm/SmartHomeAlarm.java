package ru.sbt.mipt.oop.alarm;

public class SmartHomeAlarm implements Alarm {
    private AlarmState condition = new DeactivatedAlarmState(this);

    @Override
    public void deactivate(String code) {
        condition = condition.deactivate(code);
    }

    @Override
    public void activate(String code) {
        condition = condition.activate(code);
    }

    @Override
    public void trigger() {
        condition = condition.trigger();
    }

    @Override
    public boolean isDeactivated() {
        return condition instanceof DeactivatedAlarmState;
    }
}
