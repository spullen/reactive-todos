package net.scottpullen.tasks.responses;

import net.scottpullen.tasks.entities.TaskId;

import java.io.Serializable;

public class TaskCreateResponse implements Serializable {
    TaskId id;

    public TaskCreateResponse(TaskId id) {
        this.id = id;
    }
}
