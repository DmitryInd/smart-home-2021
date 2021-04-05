package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.SmartHomeAlarm;
import ru.sbt.mipt.oop.command.DummySenderCommands;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.alarm.*;
import ru.sbt.mipt.oop.log.*;
import ru.sbt.mipt.oop.notification.SenderNotifications;
import ru.sbt.mipt.oop.notification.SmsSenderNotifications;
import ru.sbt.mipt.oop.smarthome.JsonSmartHomeRecorder;
import ru.sbt.mipt.oop.smarthome.SmartHome;
import ru.sbt.mipt.oop.smarthome.SmartHomeRecorder;

import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        SmartHomeRecorder homeReader = new JsonSmartHomeRecorder("smart-home-1.js");
        SmartHome smartHome = homeReader.readSmartHome();

        Alarm alarm = new SmartHomeAlarm();
        SenderNotifications senderNotifications = new SmsSenderNotifications();
        OutputStream output = new ConsoleOutputStream();
        List<SmartHomeHandler> handlersList = Arrays.asList(
                new DecoratorWithAlarmSmartHomeHandler(
                        senderNotifications, new DoorSmartHomeHandler(smartHome, output), alarm),
                new DecoratorWithAlarmSmartHomeHandler(
                        senderNotifications, new LightSmartHomeHandler(smartHome, output), alarm),
                new DecoratorWithAlarmSmartHomeHandler(
                        senderNotifications, new EntranceSmartHomeHandler(smartHome, new DummySenderCommands()), alarm),
                new DecoratorWithAlarmSmartHomeHandler(
                        senderNotifications, new DeactivateSmartHomeHandler(alarm, senderNotifications), alarm),
                new DecoratorWithAlarmSmartHomeHandler(
                        senderNotifications, new ActivateAlarmSmartHomeHandler(alarm, senderNotifications), alarm)
        );

        // начинаем цикл обработки событий
        ReceiverEvents receiver = new SmartHomeReceiverEvents(handlersList, output);
        receiver.handleEvents(new TestingEventsSource());
    }
}
