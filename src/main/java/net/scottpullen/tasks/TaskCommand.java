package net.scottpullen.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TaskCommand implements Serializable {
    private String content;
    private String status;
    private Integer priority;
    private LocalDateTime dueDate;

    public TaskCommand() { super(); }

    public TaskCommand(final String content, final String status, final Integer priority,
                       final LocalDateTime dueDate) {
        this.content = content;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public void setContent(final String content) {
        this.content = content;
    }
    public String getContent() { return content; }

    public void setStatus(final String status) {
        this.status = status;
    }
    public String getStatus() { return status; }

    public void setPriority(final Integer priority) {
        this.priority = priority;
    }
    public Integer getPriority() { return priority; }

    public void setDueDate(final LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public LocalDateTime getDueDate() { return dueDate; }
}
