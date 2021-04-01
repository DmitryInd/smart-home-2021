package ru.sbt.mipt.oop.alarm;

public class TriggeredAlarm implements Alarm {
    private final BaseAlarm context;

    public TriggeredAlarm(BaseAlarm context) {
        this.context = context;
    }

    @Override
    public void deactivate(String code) {
        if (context.isCorrectCode(code)) {
            context.changeState(new DeactivatedAlarm(context));
        }
    }

    @Override
    public void activate(String code) {}

    @Override
    public void trigger() {}

    @Override
    public boolean isDeactivated() {
        return false;
    }
}
