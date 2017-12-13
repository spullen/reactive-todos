package net.scottpullen.tasks.services;

import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.tasks.commands.CreateTaskCommand;
import net.scottpullen.tasks.commands.DeleteTaskCommand;
import net.scottpullen.tasks.commands.UpdateTaskCommand;
import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.entities.TaskStatus;
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
        return Single.just(command)
            .flatMap(this::validateCreateCommand)
            .flatMap((validatedCommand) -> this.buildTaskFromCreateCommand(validatedCommand, user))
            .flatMap(taskRepository::create)
            .map(task -> Optional.of(new TaskCreateResponse(task.getId())));
    }

    private Single<CreateTaskCommand> validateCreateCommand(CreateTaskCommand command) {
        return Single.create(subscriber -> {

        });
    }

    private Single<Task> buildTaskFromCreateCommand(CreateTaskCommand command, User user) {
        return taskRepository.nextId()
            .map(taskId -> {
                return Task.builder()
                    .withId(taskId)
                    .withUserId(user.getId())
                    .withContent(command.getContent())
                    .withNotes(command.getNotes())
                    .withStatus(TaskStatus.PENDING)
                    .withPriority(command.getPriority())
                    .withDueDate(command.getDueDate())
                    .withInitializedTimestamps()
                    .build();
            });
    }

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
