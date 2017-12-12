package net.scottpullen.tasks.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.tasks.commands.DeleteTaskCommand;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.services.TaskService;
import net.scottpullen.users.entities.User;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import java.util.HashMap;

import static ratpack.jackson.Jackson.json;

public class TaskDeleteHandler implements Handler {

    private final Scheduler scheduler;
    private final TaskService taskService;

    @Inject
    public TaskDeleteHandler(Scheduler scheduler, TaskService taskService) {
        this.scheduler = scheduler;
        this.taskService = taskService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        User currentUser = ctx.get(User.class);
        TaskId taskId = ctx.get(TaskId.class);

        taskService.perform(new DeleteTaskCommand(taskId), currentUser)
            .observeOn(scheduler)
            .toObservable()
            .compose(RxRatpack::bindExec)
            .subscribe(
                (t) -> {}, // No-op, translating Completable -> Observable to get it to work with RxRatpack bindings
                error -> {
                    if(error instanceof ValidationException) {
                        ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                        ctx.render(json(((ValidationException) error).getValidationResult()));
                    } else {
                        ctx.clientError(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                    }
                },
                () -> ctx.getResponse().status(HttpResponseStatus.NO_CONTENT.code()).send()
            );
    }
}
