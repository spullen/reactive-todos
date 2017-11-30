package net.scottpullen.tasks.jooq.converters;

import net.scottpullen.common.jooq.converters.EntityIdConverter;
import net.scottpullen.tasks.entities.TaskId;

public class TaskIdConverter extends EntityIdConverter<TaskId> {
    public TaskIdConverter() {
        super(TaskId.class);
    }
}
