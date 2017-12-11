package net.scottpullen.tasks.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.tasks.commands.CreateTaskCommand;
import net.scottpullen.tasks.services.TaskService;
import net.scottpullen.users.entities.User;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class TaskCreateHandler implements Handler {

    private final Scheduler scheduler;
    private final TaskService taskService;

    @Inject
    public TaskCreateHandler(Scheduler scheduler, TaskService taskService) {
        this.scheduler = scheduler;
        this.taskService = taskService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        User currentUser = ctx.get(User.class);

        ctx.parse(fromJson(CreateTaskCommand.class))
            .onError((Throwable t) -> ctx.clientError(HttpResponseStatus.BAD_REQUEST.code()))
            .to(RxRatpack::single)
            .observeOn(scheduler)
            .flatMap(command -> taskService.perform(command, currentUser))
            .toObservable()
            .compose(RxRatpack::bindExec)
            .subscribe(
                maybeTaskCreateResponse -> {
                    if(maybeTaskCreateResponse.isPresent()) {
                        ctx.render(json(maybeTaskCreateResponse.get()));
                    } else {
                        ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                        // Render something useful, better way of handling?
                        ctx.render("Failed to create task");
                    }
                },
                error -> {
                    if(error instanceof ValidationException) {
                        ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                        ctx.render(json(((ValidationException) error).getValidationResult()));
                    } else {
                        ctx.clientError(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                    }
                }
            );
    }
}
