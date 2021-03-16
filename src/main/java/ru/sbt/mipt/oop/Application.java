package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.smarthome.JsonSmartHomeRecorder;
import ru.sbt.mipt.oop.smarthome.SmartHome;
import ru.sbt.mipt.oop.smarthome.SmartHomeRecorder;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String... args) {
        // считываем состояние дома из файла
        SmartHomeRecorder homeReader = new JsonSmartHomeRecorder("smart-home-1.js");
        SmartHome smartHome = homeReader.readSmartHome();
        OutputStream output = new ConsoleOutputStream();

        List<SmartHomeHandler> handlersList = new ArrayList<>();
        handlersList.add(new DoorSmartHomeHandler(smartHome, output));
        handlersList.add(new LightSmartHomeHandler(smartHome, output));
        handlersList.add(new EntranceSmartHomeHandler(smartHome));
        // начинаем цикл обработки событий
        ReceiverEvents receiver = new SmartHomeReceiverEvents(handlersList, output);
        receiver.handleEvents(new TestingEventsSource());
    }
}
