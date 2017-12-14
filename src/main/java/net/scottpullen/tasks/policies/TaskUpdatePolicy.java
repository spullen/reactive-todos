package net.scottpullen.tasks.policies;

import net.scottpullen.tasks.entities.Task;
import net.scottpullen.users.entities.User;

public class TaskUpdatePolicy {
    private final Task task;
    private final User user;

    public TaskUpdatePolicy(final Task task, final User user) {
        this.task = task;
        this.user = user;
    }

    public boolean isAllowed() {
        return task.getUserId() == user.getId();
    }
}
