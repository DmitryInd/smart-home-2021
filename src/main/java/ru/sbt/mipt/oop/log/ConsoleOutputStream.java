package ru.sbt.mipt.oop.log;

public class ConsoleOutputStream implements OutputStream {
    @Override
    public void sendLog(String log)  {
        System.out.println(log);
    }
}
