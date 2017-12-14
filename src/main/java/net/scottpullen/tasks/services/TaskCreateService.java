package net.scottpullen.tasks.services;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.tasks.commands.CreateTaskCommand;
import net.scottpullen.tasks.commands.DeleteTaskCommand;
import net.scottpullen.tasks.commands.UpdateTaskCommand;
import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.tasks.responses.TaskCreateResponse;
import net.scottpullen.tasks.validators.CreateTaskCommandValidator;
import net.scottpullen.users.entities.User;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

public class TaskCreateService {

    private final TaskRepository taskRepository;

    public TaskCreateService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Create a task
     *
     * Validates the create command
     * Creates task record
     *
     * @param command
     * @param user
     * @return
     */
    public Single<TaskCreateResponse> perform(CreateTaskCommand command, User user) {
        return Single.just(command)
            .flatMap(this::validateCreateCommand)
            .flatMap((validatedCommand) -> this.buildTaskFromCreateCommand(validatedCommand, user))
            .flatMap(taskRepository::create)
            .map(task -> new TaskCreateResponse(task.getId()));
    }

    private Single<CreateTaskCommand> validateCreateCommand(CreateTaskCommand command) {
        return Single.create(subscriber -> {
            try {
                ComplexResult validationResult = FluentValidator.checkAll()
                    .on(command, new CreateTaskCommandValidator())
                    .doValidate()
                    .result(toComplex());

                if(validationResult.isSuccess()) {
                    subscriber.onSuccess(command);
                } else {
                    subscriber.onError(new ValidationException(validationResult));
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
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
}
