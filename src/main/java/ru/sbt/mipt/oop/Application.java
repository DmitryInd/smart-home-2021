package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.command.DummySenderCommands;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.alarm.BaseAlarmSmartHomeHandler;
import ru.sbt.mipt.oop.log.*;
import ru.sbt.mipt.oop.notification.SmsSenderNotificationsImpl;
import ru.sbt.mipt.oop.smarthome.JsonSmartHomeRecorder;
import ru.sbt.mipt.oop.smarthome.SmartHome;
import ru.sbt.mipt.oop.smarthome.SmartHomeRecorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        SmartHomeRecorder homeReader = new JsonSmartHomeRecorder("smart-home-1.js");
        SmartHome smartHome = homeReader.readSmartHome();
        OutputStream output = new ConsoleOutputStream();

        List<SmartHomeHandler> sensorHandlersList = Arrays.asList(
                new DoorSmartHomeHandler(smartHome, output),
                new LightSmartHomeHandler(smartHome, output),
                new EntranceSmartHomeHandler(smartHome, new DummySenderCommands())
        );

        List<SmartHomeHandler> handlersList = Arrays.asList(
          new BaseAlarmSmartHomeHandler(sensorHandlersList, new SmsSenderNotificationsImpl())
        );
        // начинаем цикл обработки событий
        ReceiverEvents receiver = new SmartHomeReceiverEvents(handlersList, output);
        receiver.handleEvents(new TestingEventsSource());
    }
}
