package ru.sbt.mipt.oop.notification;

public class SmsSenderNotifications implements SenderNotifications {
    @Override
    public void send(String notificaton) {
        System.out.println(notificaton);
    }
}
