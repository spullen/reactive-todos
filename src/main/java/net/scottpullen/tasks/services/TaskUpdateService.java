package net.scottpullen.tasks.services;

import io.reactivex.Completable;
import net.scottpullen.tasks.commands.UpdateTaskCommand;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.users.entities.User;

public class TaskUpdateService {
    private final TaskRepository taskRepository;

    public TaskUpdateService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Perform an update on a task
     *
     * Validates update command
     * Authorizes that the user is allowed to update the task
     * Updates task record
     *
     * @param command
     * @param user
     * @return
     */
    public Completable perform(UpdateTaskCommand command, User user) {
        // validate command
        // fetch existing task
        // authorize user can modify task
        // determine if anything has changed
        // update task object (build new?), update updated at
        // determine whether to set completed at (to time or back to null)
        // save task

        return Completable.create(subscriber -> {

        });
    }
}
