package net.scottpullen.users.entities;

import net.scottpullen.common.entities.EntityId;

import java.util.UUID;

public class UserId extends EntityId {
    public UserId(UUID id) {
        super(id);
    }

    public UserId(String id) {
        super(id);
    }
}
