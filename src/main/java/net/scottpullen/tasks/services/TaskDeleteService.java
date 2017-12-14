package net.scottpullen.tasks.services;

import io.reactivex.Completable;
import net.scottpullen.tasks.commands.DeleteTaskCommand;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.users.entities.User;

public class TaskDeleteService {
    private final TaskRepository taskRepository;

    public TaskDeleteService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Perform a delete on a task (really just setting the deleted at field)
     *
     * Validates update command
     * Authorizes that the user is allowed to update the task
     * Updates task record
     *
     * @param command
     * @param user
     * @return
     */
    public Completable perform(DeleteTaskCommand command, User user) {
        // Validate command
        // fetch existing task
        // authorize user can delete task
        // update task object (build new?), update deleted at
        // save task

        return Completable.create(subscriber -> {

        });
    }
}
