package net.scottpullen.tasks.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.tasks.commands.CreateTaskCommand;
import net.scottpullen.tasks.services.TaskCreateService;
import net.scottpullen.users.entities.User;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class TaskCreateHandler implements Handler {

    private final Scheduler scheduler;
    private final TaskCreateService taskCreateService;

    @Inject
    public TaskCreateHandler(Scheduler scheduler, TaskCreateService taskCreateService) {
        this.scheduler = scheduler;
        this.taskCreateService = taskCreateService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        User currentUser = ctx.get(User.class);

        ctx.parse(fromJson(CreateTaskCommand.class))
            .onError((Throwable t) -> ctx.clientError(HttpResponseStatus.BAD_REQUEST.code()))
            .to(RxRatpack::single)
            .observeOn(scheduler)
            .flatMap(command -> taskCreateService.perform(command, currentUser))
            .toObservable()
            .compose(RxRatpack::bindExec)
            .subscribe(
                taskCreateResponse -> ctx.render(json(taskCreateResponse)),
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
