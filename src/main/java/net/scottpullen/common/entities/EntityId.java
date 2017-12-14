package net.scottpullen.common.entities;

import java.util.UUID;

import static net.scottpullen.common.ArgumentPreconditions.required;

public class EntityId {
    private final UUID id;

    public EntityId(UUID id) {
        required(id, "id required");

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
