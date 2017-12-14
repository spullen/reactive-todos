package net.scottpullen.tasks.services;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.NotAuthorizedException;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.tasks.commands.UpdateTaskCommand;
import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.policies.TaskUpdatePolicy;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.tasks.validators.UpdateTaskCommandValidator;
import net.scottpullen.users.entities.User;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

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
        return Single.just(command)
            .flatMap(this::validateCommand)
            .flatMap(validatedCommand -> taskRepository.find(validatedCommand.getId()))
            .flatMap(task -> authorize(task, user))
            .map(task -> {
                // build new task
                // set completed at if the task is complete
                if(command.getStatus() == TaskStatus.COMPLETED) {

                }
                return task;
            })
            .flatMap(taskRepository::update)
            .toCompletable();
    }

    private Single<UpdateTaskCommand> validateCommand(UpdateTaskCommand command) {
        return Single.create(subscriber -> {
            try {
                ComplexResult validationResult = FluentValidator.checkAll()
                    .on(command, new UpdateTaskCommandValidator())
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

    private Single<Task> authorize(Task task, User user) {
        return Single.create(subscribe -> {
            TaskUpdatePolicy policy = new TaskUpdatePolicy(task, user);

            if(policy.isAllowed()) {
                subscribe.onSuccess(task);
            } else {
                subscribe.onError(new NotAuthorizedException("User " + user.getId() + " is not authorized to update Task " + task.getId()));
            }
        });
    }
}
