package net.scottpullen.tasks.jooq.bindings;

import net.scottpullen.common.jooq.bindings.EntityIdBinding;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.jooq.converters.TaskIdConverter;

public class TaskIdBinding extends EntityIdBinding<TaskIdConverter, TaskId> {
    private static final TaskIdConverter CONVERTER = new TaskIdConverter();

    @Override
    public TaskIdConverter converter() { return CONVERTER; }
}