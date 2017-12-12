package net.scottpullen.tasks.entities;

import net.scottpullen.users.entities.User;
import net.scottpullen.users.entities.UserId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class Task {
    private final TaskId id;
    private final UserId userId;
    private final String content;
    private final String notes;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final LocalDateTime dueDate;
    private final LocalDateTime completedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Task(final TaskId id, UserId userId, String content, String notes, TaskStatus status, TaskPriority priority,
                LocalDateTime dueDate, LocalDateTime completedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.notes = notes;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public TaskId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getNotes() {
        return notes;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) {
            return false;
        } else if(other == this) {
            return true;
        } else if(!getClass().isAssignableFrom(other.getClass())) {
            return false;
        } else {
            Task otherTask = (Task) other;
            return new EqualsBuilder()
                .append(id, otherTask.getId())
                .append(userId, otherTask.getUserId())
                .append(content, otherTask.getContent())
                .append(notes, otherTask.getNotes())
                .append(status, otherTask.getStatus())
                .append(priority, otherTask.getPriority())
                .append(dueDate, otherTask.getDueDate())
                .append(completedAt, otherTask.getCompletedAt())
                .append(createdAt, otherTask.getCreatedAt())
                .append(updatedAt, otherTask.getUpdatedAt())
                .isEquals();
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .append(content)
            .append(notes)
            .append(status)
            .append(priority)
            .append(dueDate)
            .append(completedAt)
            .append(createdAt)
            .append(updatedAt)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "Task(id" + id + ", userId" + userId + ")";
    }
}
