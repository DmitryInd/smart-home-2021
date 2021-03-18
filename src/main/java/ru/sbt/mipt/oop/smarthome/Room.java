package ru.sbt.mipt.oop.smarthome;

import ru.sbt.mipt.oop.actions.Actionable;
import ru.sbt.mipt.oop.actions.SmartHomeAction;
import ru.sbt.mipt.oop.iterators.*;

import java.util.Collection;

import static ru.sbt.mipt.oop.iterators.IteratorOfRoomType.*;

public class Room implements Actionable, InternalIterable, SmartHomeObject {
    private Collection<Light> lights;
    private Collection<Door> doors;
    private String name;
    private IteratorOfRoomType iteratorType = DOOR;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public Collection<Light> getLights() {
        return lights;
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public String getName() {
        return name;
    }

    public void setIteratorType(IteratorOfRoomType roomIteratorType) {
        this.iteratorType = roomIteratorType;
    }

    @Override
    public void execute(SmartHomeAction smartHomeAction) {
        smartHomeAction.performOn(this);
        setIteratorType(DOOR);
        createIterator().performForAll(smartHomeAction);
        setIteratorType(LIGHT);
        createIterator().performForAll(smartHomeAction);
    }

    @Override
    public InternalIterator createIterator() {
        return iteratorType.loadIterator(this);
    }
}
