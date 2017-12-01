package net.scottpullen.tasks.jooq.converters;

import net.scottpullen.tasks.entities.TaskPriority;
import org.jooq.Converter;

public class TaskPriorityConverter implements Converter<Integer, TaskPriority> {
    @Override
    public TaskPriority from(Integer t) {
        if(t == null) {
            return null;
        }

        return TaskPriority.forValue(t);
    }

    @Override
    public Integer to(TaskPriority u) {
        return u == null ? null : u.getValue();
    }

    @Override
    public Class<Integer> fromType() {
        return Integer.class;
    }

    @Override
    public Class<TaskPriority> toType() {
        return TaskPriority.class;
    }
}
