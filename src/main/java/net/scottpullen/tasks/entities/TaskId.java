package net.scottpullen.tasks.entities;

import net.scottpullen.common.entities.EntityId;

import java.util.UUID;

public class TaskId extends EntityId {
    public TaskId(UUID id) {
        super(id);
    }

    public TaskId(String id) {
        super(id);
    }
}
