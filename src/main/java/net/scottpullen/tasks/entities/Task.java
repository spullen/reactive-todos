package net.scottpullen.tasks.entities;

import net.scottpullen.users.entities.UserId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

public class Task {
    private final TaskId id;
    private final UserId userId;
    private final String content;
    private final String notes;
    private final TaskStatus status;
    private final TaskPriority priority;
    private final Optional<LocalDateTime> dueDate;
    private final Optional<LocalDateTime> completedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Optional<LocalDateTime> deletedAt;

    public static class Builder {
        private TaskId id;
        private UserId userId;
        private String content;
        private String notes;
        private TaskStatus status;
        private TaskPriority priority;
        private Optional<LocalDateTime> dueDate = Optional.empty();
        private Optional<LocalDateTime> completedAt = Optional.empty();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Optional<LocalDateTime> deletedAt = Optional.empty();

        public Builder withId(TaskId id) {
            this.id = id;
            return this;
        }

        public Builder withUserId(UserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder withStatus(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Builder withPriority(TaskPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder withDueDate(LocalDateTime dueDate) {
            this.dueDate = Optional.ofNullable(dueDate);
            return this;
        }

        public Builder withCompletedAt(LocalDateTime completedAt) {
            this.completedAt = Optional.ofNullable(completedAt);
            return this;
        }

        public Builder withInitializedTimestamps() {
            LocalDateTime now = LocalDateTime.now();
            createdAt = now;
            updatedAt = now;
            return this;
        }

        public Builder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder withDeletedAt(LocalDateTime deletedAt) {
            this.deletedAt = Optional.ofNullable(deletedAt);
            return this;
        }

        public Task build() {
            return new Task(
                id,
                userId,
                content,
                notes,
                status,
                priority,
                dueDate,
                completedAt,
                createdAt,
                updatedAt,
                deletedAt
            );
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private Task(final TaskId id, UserId userId, String content, String notes, TaskStatus status, TaskPriority priority,
                Optional<LocalDateTime> dueDate, Optional<LocalDateTime> completedAt, LocalDateTime createdAt,
                LocalDateTime updatedAt, Optional<LocalDateTime> deletedAt) {
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
        this.deletedAt = deletedAt;
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

    public Optional<LocalDateTime> getDueDate() {
        return dueDate;
    }

    public Optional<LocalDateTime> getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Optional<LocalDateTime> getDeletedAt() {
        return deletedAt;
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
                .append(deletedAt, otherTask.getDeletedAt())
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
            .append(deletedAt)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "Task(id" + id + ", userId" + userId + ")";
    }
}
