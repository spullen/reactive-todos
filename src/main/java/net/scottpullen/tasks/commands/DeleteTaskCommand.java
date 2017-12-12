package net.scottpullen.tasks.commands;

import net.scottpullen.tasks.entities.TaskId;

import java.io.Serializable;

public class DeleteTaskCommand implements Serializable {
    private TaskId id;

    public DeleteTaskCommand() {
        super();
    }

    public DeleteTaskCommand(TaskId id) {
        this.id = id;
    }

    public void setId(TaskId id) { this.id = id; }
    public TaskId getId() { return id; }
}
