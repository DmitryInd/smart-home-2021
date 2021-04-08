package ru.sbt.mipt.oop.smarthome.object.recorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sbt.mipt.oop.smarthome.object.SmartHome;
import ru.sbt.mipt.oop.smarthome.object.SmartHomeRecorder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonSmartHomeRecorder implements SmartHomeRecorder {
    private final String pathToFile;

    public JsonSmartHomeRecorder(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public SmartHome readSmartHome() {
        Gson gson = new Gson();
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(pathToFile)));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't open the file " + pathToFile, e);
        }

        return gson.fromJson(json, SmartHome.class);
    }

    @Override
    public void saveSmartHome(SmartHome smartHome) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(smartHome);
        System.out.println(jsonString);
        Path fullPath = Paths.get(pathToFile);
        try (BufferedWriter writer = Files.newBufferedWriter(fullPath)) {
            writer.write(jsonString);
        } catch (IOException e){
            throw new RuntimeException("Couldn't write to the file " + pathToFile, e);
        }
    }
}