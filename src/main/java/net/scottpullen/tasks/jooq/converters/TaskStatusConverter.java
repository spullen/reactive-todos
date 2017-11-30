package net.scottpullen.tasks.jooq.converters;

import net.scottpullen.tasks.entities.TaskStatus;
import org.jooq.impl.EnumConverter;

public class TaskStatusConverter extends EnumConverter<String, TaskStatus> {
    public TaskStatusConverter() {
        super(String.class, TaskStatus.class);
    }
}