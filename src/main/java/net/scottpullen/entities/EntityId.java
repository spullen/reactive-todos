package net.scottpullen.entities;

import java.util.UUID;

public class EntityId {
    private final UUID id;

    public EntityId(UUID id) {
        this.id = id;
    }

    public EntityId(String id) {
        this(UUID.fromString(id));
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
