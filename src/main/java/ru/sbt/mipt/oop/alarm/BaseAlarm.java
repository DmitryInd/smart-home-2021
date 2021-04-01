package ru.sbt.mipt.oop.alarm;

public class BaseAlarm implements Alarm {
    private Alarm condition = new DeactivatedAlarm(this);
    private String code = "";

    @Override
    public void deactivate(String code) {
        condition.deactivate(code);
    }

    @Override
    public void activate(String code) {
        condition.activate(code);
    }

    @Override
    public void trigger() {
        condition.trigger();
    }

    @Override
    public boolean isDeactivated() {
        return condition.isDeactivated();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCorrectCode(String code) {
        return this.code.equals(code);
    }

    public void changeState(Alarm condition) {
        this.condition = condition;
    }
}
