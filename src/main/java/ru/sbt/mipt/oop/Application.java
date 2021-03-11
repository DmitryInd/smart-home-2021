package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.ReceiverEvents;
import ru.sbt.mipt.oop.events.SmartHomeReceiverEvents;
import ru.sbt.mipt.oop.events.TestingEventsSource;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.smarthome.JsonSmartHomeRecorder;
import ru.sbt.mipt.oop.smarthome.SmartHome;
import ru.sbt.mipt.oop.smarthome.SmartHomeRecorder;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        SmartHomeRecorder homeReader = new JsonSmartHomeRecorder("smart-home-1.js");
        SmartHome smartHome = homeReader.getSmartHome();
        // начинаем цикл обработки событий
        ReceiverEvents receiver = new SmartHomeReceiverEvents(smartHome, new ConsoleOutputStream());
        receiver.handleEvents(new TestingEventsSource());
    }
}
