package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.SmartHomeAlarm;
import ru.sbt.mipt.oop.command.DummySenderCommands;
import ru.sbt.mipt.oop.events.DoorSmartHomeHandler;
import ru.sbt.mipt.oop.events.EntranceSmartHomeHandler;
import ru.sbt.mipt.oop.events.LightSmartHomeHandler;
import ru.sbt.mipt.oop.events.SmartHomeHandler;
import ru.sbt.mipt.oop.events.alarm.ActivateAlarmSmartHomeHandler;
import ru.sbt.mipt.oop.events.alarm.DeactivateSmartHomeHandler;
import ru.sbt.mipt.oop.events.alarm.DecoratorWithAlarmSmartHomeHandler;
import ru.sbt.mipt.oop.events.coolcompany.adapter.AdapterEventHandler;
import ru.sbt.mipt.oop.log.ConsoleOutputStream;
import ru.sbt.mipt.oop.notification.SmsSenderNotifications;
import ru.sbt.mipt.oop.smarthome.JsonSmartHomeRecorder;
import ru.sbt.mipt.oop.smarthome.SmartHome;
import ru.sbt.mipt.oop.smarthome.SmartHomeRecorder;

import java.util.Arrays;
import java.util.List;

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
                                        smartHomeAlarm()))));

        return manager;
    }

    @Bean
    List<SmartHomeHandler> handlersList() {
        return Arrays.asList(
                new DoorSmartHomeHandler(smartHome(), consoleOutputStream()),
                new LightSmartHomeHandler(smartHome(), consoleOutputStream()),
                new EntranceSmartHomeHandler(smartHome(), dummySenderCommands()),
                new DeactivateSmartHomeHandler(smartHomeAlarm(), smsSenderNotifications()),
                new ActivateAlarmSmartHomeHandler(smartHomeAlarm(), smsSenderNotifications())
        );
    }

    @Bean
    DummySenderCommands dummySenderCommands() {
        return new DummySenderCommands();
    }

    @Bean
    ConsoleOutputStream consoleOutputStream() {
        return new ConsoleOutputStream();
    }

    @Bean
    SmsSenderNotifications smsSenderNotifications() {
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
