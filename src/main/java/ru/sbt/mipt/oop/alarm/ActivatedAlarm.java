package ru.sbt.mipt.oop.alarm;

public class ActivatedAlarm implements Alarm {
    private final BaseAlarm context;

    public ActivatedAlarm(BaseAlarm context) {
        this.context = context;
    }

    @Override
    public void deactivate(String code) {
        if (context.isCorrectCode(code)) {
            context.changeState(new DeactivatedAlarm(context));
        } else {
            trigger();
        }
    }

    @Override
    public void activate(String code) {}

    @Override
    public void trigger() {
        context.changeState(new TriggeredAlarm(context));
    }

    @Override
    public boolean isDeactivated() {
        return false;
    }
}
