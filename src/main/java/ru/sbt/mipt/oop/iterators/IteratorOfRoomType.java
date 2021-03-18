package ru.sbt.mipt.oop.iterators;

import ru.sbt.mipt.oop.smarthome.Room;

public enum IteratorOfRoomType {
    DOOR {
        @Override
        public InternalIterator loadIterator(Room currentRoom) {
            return new DoorInternalIterator(currentRoom);
        }
    },
    LIGHT {
        @Override
        public InternalIterator loadIterator(Room currentRoom) {
            return new LightInternalIterator(currentRoom);
        }
    };

    public abstract InternalIterator loadIterator(Room currentRoom);
}
