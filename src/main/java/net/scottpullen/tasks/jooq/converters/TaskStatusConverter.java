package net.scottpullen.tasks.jooq.converters;

import net.scottpullen.tasks.entities.TaskStatus;
import org.jooq.Converter;

public class TaskStatusConverter implements Converter<String, TaskStatus> {
    @Override
    public TaskStatus from(String t) {
        if(t == null) {
            return null;
        }

        return TaskStatus.forValue(t);
    }

    @Override
    public String to(TaskStatus u) {
        return u == null ? null : u.getValue();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<TaskStatus> toType() {
        return TaskStatus.class;
    }
}