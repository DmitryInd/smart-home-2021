package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.SmartHomeAlarm;
import ru.sbt.mipt.oop.command.DummySenderCommands;
import ru.sbt.mipt.oop.command.SenderCommands;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.alarm.ActivateAlarmSmartHomeHandler;
import ru.sbt.mipt.oop.events.alarm.DeactivateSmartHomeHandler;
import ru.sbt.mipt.oop.events.alarm.DecoratorWithAlarmSmartHomeHandler;
import ru.sbt.mipt.oop.events.coolcompany.adapter.AdapterEventHandler;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.log.OutputStream;
import ru.sbt.mipt.oop.notification.SenderNotifications;
import ru.sbt.mipt.oop.notification.SmsSenderNotifications;
import ru.sbt.mipt.oop.smarthome.JsonSmartHomeRecorder;
import ru.sbt.mipt.oop.smarthome.SmartHome;
import ru.sbt.mipt.oop.smarthome.SmartHomeRecorder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ApplicationConfiguration {
    @Bean
    SensorEventsManager sensorEventsManager() {
        SensorEventsManager manager = new SensorEventsManager();
        handlersList().forEach(handler ->
                manager.registerEventHandler(
                        new AdapterEventHandler(
                                new DecoratorWithAlarmSmartHomeHandler(
                                        smsSenderNotifications(),
                                        handler,
                                        smartHomeAlarm()), dictionary())));

        return manager;
    }

    @Bean
    Map<String, EventType> dictionary(){
        Map<String, EventType> dictionary = new HashMap<>();
        dictionary.put("LightIsOn", EventType.LIGHT_ON);
        dictionary.put("LightIsOff", EventType.LIGHT_OFF);
        dictionary.put("DoorIsOpen", EventType.DOOR_OPEN);
        dictionary.put("DoorIsClosed", EventType.DOOR_CLOSED);
        return dictionary;
    }

    @Bean
    List<SmartHomeHandler> handlersList() {
        return Arrays.asList(
                DoorSmartHomeHandler(),
                LightSmartHomeHandler(),
                EntranceSmartHomeHandler(),
                DeactivateSmartHomeHandler(),
                ActivateAlarmSmartHomeHandler()
        );
    }

    @Bean
    SmartHomeHandler DoorSmartHomeHandler() {
        return new DoorSmartHomeHandler(smartHome(), consoleOutputStream());
    }

    @Bean
    SmartHomeHandler LightSmartHomeHandler() {
        return new LightSmartHomeHandler(smartHome(), consoleOutputStream());
    }

    @Bean
    SmartHomeHandler EntranceSmartHomeHandler() {
        return new EntranceSmartHomeHandler(smartHome(), dummySenderCommands());
    }

    @Bean
    SmartHomeHandler DeactivateSmartHomeHandler() {
        return new DeactivateSmartHomeHandler(smartHomeAlarm(), smsSenderNotifications());
    }

    @Bean
    SmartHomeHandler ActivateAlarmSmartHomeHandler() {
        return new ActivateAlarmSmartHomeHandler(smartHomeAlarm(), smsSenderNotifications());
    }


    @Bean
    SenderCommands dummySenderCommands() {
        return new DummySenderCommands();
    }

    @Bean
    OutputStream consoleOutputStream() {
        return new ConsoleOutputStream();
    }

    @Bean
    SenderNotifications smsSenderNotifications() {
        return new SmsSenderNotifications();
    }

    @Bean
    Alarm smartHomeAlarm() {
        return new SmartHomeAlarm();
    }

    @Bean
    SmartHome smartHome() {
        SmartHomeRecorder homeReader = new JsonSmartHomeRecorder("smart-home-1.js");
        return homeReader.readSmartHome();
    }
}
