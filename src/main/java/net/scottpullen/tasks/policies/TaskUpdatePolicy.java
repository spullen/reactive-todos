package net.scottpullen.tasks.policies;

import net.scottpullen.tasks.entities.Task;
import net.scottpullen.users.entities.User;

import static net.scottpullen.common.ArgumentPreconditions.required;

public class TaskUpdatePolicy {
    private final Task task;
    private final User user;

    public TaskUpdatePolicy(final Task task, final User user) {
        required(task, "Task required");
        required(user, "User required");

        this.task = task;
        this.user = user;
    }

    public boolean isAllowed() {
        return task.getUserId().equals(user.getId());
    }
}
