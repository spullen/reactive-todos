package net.scottpullen.tasks.entities;

import java.util.HashMap;
import java.util.Map;

public enum TaskStatus {
    PENDING("pending"),
    COMPLETED("completed"),
    CANCELED("canceled");

    private static final Map<String, TaskStatus> TASK_STATUS_BY_VALUE = new HashMap<String, TaskStatus>();

    static {
        for (TaskStatus type : TaskStatus.values()) {
            TASK_STATUS_BY_VALUE.put(type.id, type);
        }
    }

    private final String id;

    TaskStatus(String id) {
        this.id = id;
    }

    public static TaskStatus forValue(String value) {
        return TASK_STATUS_BY_VALUE.get(value);
    }

}
