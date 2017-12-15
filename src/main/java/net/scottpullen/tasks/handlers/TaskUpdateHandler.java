package net.scottpullen.tasks.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.common.exceptions.NotAuthorizedException;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.tasks.commands.UpdateTaskCommand;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.services.TaskUpdateService;
import net.scottpullen.users.entities.User;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static net.scottpullen.common.ArgumentPreconditions.required;
import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class TaskUpdateHandler implements Handler {

    private final Scheduler scheduler;
    private final TaskUpdateService taskUpdateService;

    @Inject
    public TaskUpdateHandler(Scheduler scheduler, TaskUpdateService taskUpdateService) {
        required(scheduler, "Scheduler required");
        required(taskUpdateService, "TaskUpdateService required");

        this.scheduler = scheduler;
        this.taskUpdateService = taskUpdateService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        User currentUser = ctx.get(User.class);

        ctx.parse(fromJson(UpdateTaskCommand.class))
            .onError((Throwable t) -> ctx.clientError(HttpResponseStatus.BAD_REQUEST.code()))
            .to(RxRatpack::single)
            .observeOn(scheduler)
            .map(command -> {
                command.setId(ctx.get(TaskId.class));
                return command;
            })
            .flatMapCompletable(command -> taskUpdateService.perform(command, currentUser))
            .toObservable()
            .compose(RxRatpack::bindExec)
            .subscribe(
                (t) -> {}, // No-op, translating Completable -> Observable to get it to work with RxRatpack bindings
                error -> {
                    if(error instanceof ValidationException) {
                        ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                        ctx.render(json(((ValidationException) error).getValidationResult()));
                    } else if(error instanceof NotAuthorizedException) {
                        ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
                    } else {
                        ctx.clientError(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                    }
                },
                () -> ctx.getResponse().status(HttpResponseStatus.NO_CONTENT.code()).send()
            );
    }
}
