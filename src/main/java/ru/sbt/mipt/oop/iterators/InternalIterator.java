package ru.sbt.mipt.oop.iterators;

import ru.sbt.mipt.oop.actions.SmartHomeAction;

public interface InternalIterator {
    void performForAll(SmartHomeAction action);
}
