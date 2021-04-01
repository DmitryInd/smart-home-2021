package ru.sbt.mipt.oop.alarm;


public class DeactivatedAlarm implements Alarm {
    private final BaseAlarm context;

    public DeactivatedAlarm(BaseAlarm context) {
        this.context = context;
    }

    @Override
    public void deactivate(String code) {}

    @Override
    public void activate(String code) {
        context.setCode(code);
        context.changeState(new ActivatedAlarm(context));
    }

    @Override
    public void trigger() {}

    @Override
    public boolean isDeactivated() {
        return true;
    }
}
