package ru.sbt.mipt.oop.remote.control;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControl;
import ru.sbt.mipt.oop.remote.Command;
import ru.sbt.mipt.oop.remote.RemoteControlBuilder;

@Configuration
public class DummyRemoteControlConfiguration {
    @Bean
    RemoteControl remoteControl() {
        RemoteControlBuilder<SmartHomeRemoteControl> builder = new SmartHomeRemoteControlBuilder();
        builder.setRcId("4");
        builder.setEventForButton("A", createDummyCommand("4A"));
        builder.getResult();

        builder.setRcId("3");
        builder.setEventForButton("A", createDummyCommand("3A"));
        builder.getResult();

        builder.setRcId("2");
        builder.setEventForButton("A", createDummyCommand("2A"));
        builder.setEventForButton("B", createDummyCommand("2B"));
        builder.getResult();

        builder.setRcId("1");
        builder.setEventForButton("C", createDummyCommand("1C"));
        builder.setEventForButton("D", createDummyCommand("1D"));
        return builder.getResult();
    }

    Command createDummyCommand(String code) {
        return () -> {
            throw new RuntimeException(code);
        };
    }
}