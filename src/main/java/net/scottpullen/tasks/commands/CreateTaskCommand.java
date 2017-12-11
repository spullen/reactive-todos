package net.scottpullen.tasks.commands;

import net.scottpullen.tasks.entities.TaskPriority;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CreateTaskCommand implements Serializable {
    private String content;
    private String notes;
    private TaskPriority priority;
    private LocalDateTime dueDate;

    public CreateTaskCommand() {
        super();
    }

    public CreateTaskCommand(String content, String notes, TaskPriority priority, LocalDateTime dueDate) {
        this.content = content;
        this.notes = notes;
        this.priority = priority;
        this.dueDate = dueDate;
    }

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
}
