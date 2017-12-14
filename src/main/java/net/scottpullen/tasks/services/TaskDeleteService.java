package net.scottpullen.tasks.services;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.NotAuthorizedException;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.tasks.commands.DeleteTaskCommand;
import net.scottpullen.tasks.commands.UpdateTaskCommand;
import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.policies.TaskUpdatePolicy;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.tasks.validators.DeleteTaskCommandValidator;
import net.scottpullen.tasks.validators.UpdateTaskCommandValidator;
import net.scottpullen.users.entities.User;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

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
        return Single.just(command)
            .flatMap(this::validateCommand)
            .flatMap(validatedCommand -> taskRepository.find(validatedCommand.getId()))
            .flatMap(task -> authorize(task, user))
            .map(task -> task.getId())
            .flatMapCompletable(taskRepository::delete);
    }

    private Single<DeleteTaskCommand> validateCommand(DeleteTaskCommand command) {
        return Single.create(subscriber -> {
            try {
                ComplexResult validationResult = FluentValidator.checkAll()
                    .on(command, new DeleteTaskCommandValidator())
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
                subscribe.onError(new NotAuthorizedException("User " + user.getId() + " is not authorized to delete Task " + task.getId()));
            }
        });
    }
}
