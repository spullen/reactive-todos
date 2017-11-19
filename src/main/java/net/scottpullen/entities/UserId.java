package net.scottpullen.entities;

import java.util.UUID;

public class UserId extends EntityId {
    public UserId(UUID id) {
        super(id);
    }

    public UserId(String id) {
        super(UUID.fromString(id));
    }
}
