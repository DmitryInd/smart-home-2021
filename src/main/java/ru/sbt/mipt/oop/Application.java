package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.command.DummySenderCommands;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.alarm.Alarm;
import ru.sbt.mipt.oop.events.alarm.BaseAlarm;
import ru.sbt.mipt.oop.events.alarm.DecoratorWithAlarmSmartHomeHandler;
import ru.sbt.mipt.oop.log.*;
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
        OutputStream output = new ConsoleOutputStream();

        Alarm alarm = new BaseAlarm(new SmsSenderNotifications());
        List<SmartHomeHandler> handlersList = Arrays.asList(
                new DecoratorWithAlarmSmartHomeHandler(new DoorSmartHomeHandler(smartHome, output), alarm),
                new DecoratorWithAlarmSmartHomeHandler(new LightSmartHomeHandler(smartHome, output), alarm),
                new DecoratorWithAlarmSmartHomeHandler(
                        new EntranceSmartHomeHandler(smartHome, new DummySenderCommands()), alarm)
        );

        // начинаем цикл обработки событий
        ReceiverEvents receiver = new SmartHomeReceiverEvents(handlersList, output);
        receiver.handleEvents(new TestingEventsSource());
    }
}
