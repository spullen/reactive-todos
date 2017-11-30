package net.scottpullen.tasks.entities;

import java.util.HashMap;
import java.util.Map;

public enum TaskPriority {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private static final Map<Integer, TaskPriority> TASK_PRIORITY_BY_VALUE = new HashMap<Integer, TaskPriority>();

    static {
        for (TaskPriority type : TaskPriority.values()) {
            TASK_PRIORITY_BY_VALUE.put(type.id, type);
        }
    }

    final Integer id;

    TaskPriority(Integer id) {
        this.id = id;
    }

    public static TaskPriority forValue(Integer value) {
        return TASK_PRIORITY_BY_VALUE.get(value);
    }
}
