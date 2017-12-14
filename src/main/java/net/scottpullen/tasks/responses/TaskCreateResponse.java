package net.scottpullen.tasks.responses;

import net.scottpullen.tasks.entities.TaskId;

import java.io.Serializable;

import static net.scottpullen.common.ArgumentPreconditions.required;

public class TaskCreateResponse implements Serializable {
    TaskId id;

    public TaskCreateResponse(TaskId id) {
        required(id, "TaskId required");

        this.id = id;
    }
}
