package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.command.DummySenderCommands;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.alarm.AlarmSmartHomeHandler;
import ru.sbt.mipt.oop.events.alarm.BaseAlarmSmartHomeHandler;
import ru.sbt.mipt.oop.events.alarm.DecoratorWithAlarmSmartHomeHandler;
import ru.sbt.mipt.oop.log.*;
import ru.sbt.mipt.oop.notification.SmsSenderNotifications;
import ru.sbt.mipt.oop.smarthome.JsonSmartHomeRecorder;
import ru.sbt.mipt.oop.smarthome.SmartHome;
import ru.sbt.mipt.oop.smarthome.SmartHomeRecorder;

import com.coolcompany.smarthome.events.*;

import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        SmartHomeRecorder homeReader = new JsonSmartHomeRecorder("smart-home-1.js");
        SmartHome smartHome = homeReader.readSmartHome();
        OutputStream output = new ConsoleOutputStream();

        AlarmSmartHomeHandler alarmSmartHomeHandler = new BaseAlarmSmartHomeHandler(new SmsSenderNotifications());
        List<SmartHomeHandler> handlersList = Arrays.asList(
                new DecoratorWithAlarmSmartHomeHandler(
                        new DoorSmartHomeHandler(smartHome, output), alarmSmartHomeHandler),
                new DecoratorWithAlarmSmartHomeHandler(
                        new LightSmartHomeHandler(smartHome, output), alarmSmartHomeHandler),
                new DecoratorWithAlarmSmartHomeHandler(
                        new EntranceSmartHomeHandler(smartHome, new DummySenderCommands()), alarmSmartHomeHandler)
        );

        // начинаем цикл обработки событий
        ReceiverEvents receiver = new SmartHomeReceiverEvents(handlersList, output);
        receiver.handleEvents(new TestingEventsSource());
    }
}
