package net.scottpullen.tasks.services;

import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.tasks.commands.CreateTaskCommand;
import net.scottpullen.tasks.commands.UpdateTaskCommand;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.tasks.responses.TaskCreateResponse;
import net.scottpullen.users.entities.User;

import java.util.Optional;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Single<Optional<TaskCreateResponse>> perform(CreateTaskCommand command, User user) {
        // validate command
        // build task object
        // create task object
        // build task create response

        return Single.create(subscriber -> {

        });
    }

    public Completable perform(UpdateTaskCommand command, User user) {
        return Completable.create(subscriber -> {

        });
    }
}
