package ru.sbt.mipt.oop.notification;

public class SmsSenderNotificationsImpl implements SenderNotifications {
    @Override
    public void send(String notificaton) {
        System.out.println(notificaton);
    }
}
