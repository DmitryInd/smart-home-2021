package ru.sbt.mipt.oop.command;

public class DummySenderCommands implements SenderCommands {
    @Override
    public void sendCommand(SensorCommand command) {
        System.out.println("Pretend we're sending command " + command);
    }
}
