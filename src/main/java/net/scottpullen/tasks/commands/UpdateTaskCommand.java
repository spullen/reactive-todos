package net.scottpullen.tasks.commands;

import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.entities.TaskPriority;
import net.scottpullen.tasks.entities.TaskStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UpdateTaskCommand implements Serializable {

    private TaskId id;
    private String content;
    private String notes;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime dueDate;

    public UpdateTaskCommand() {
        super();
    }

    public UpdateTaskCommand(String content, String notes, TaskStatus status, TaskPriority priority,
                             LocalDateTime dueDate) {
        this.content = content;
        this.notes = notes;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public UpdateTaskCommand(TaskId id, String content, String notes, TaskStatus status, TaskPriority priority,
                             LocalDateTime dueDate) {
        this.id = id;
        this.content = content;
        this.notes = notes;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public TaskId getId() { return id; }
    public void setId(TaskId id) { this.id = id; }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "TaskId " + id + ", Content " + content + ", Notes " + notes + ", Status " + status + ", Priority " + priority + ", DueDate " + dueDate;
    }
}
