package net.scottpullen.tasks.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.tasks.responses.TasksResponse;
import net.scottpullen.users.entities.User;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static ratpack.jackson.Jackson.json;

public class TaskIndexHandler implements Handler {

    private final Scheduler scheduler;
    private final TaskRepository taskRepository;

    @Inject
    public TaskIndexHandler(Scheduler scheduler, TaskRepository taskRepository) {
        this.scheduler = scheduler;
        this.taskRepository = taskRepository;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        User currentUser = ctx.get(User.class);

        TasksResponse tasksResponse = new TasksResponse();

        // TODO: make status a query param
        // TODO: add start/end dates to query params to allow filtering based on those
        taskRepository.findAllByTaskStatusAndByUserId(TaskStatus.PENDING, currentUser.getId())
            .observeOn(scheduler)
            .compose(RxRatpack::bindExec)
            .subscribe(
                tasksResponse::add,
                error -> ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code()),
                () -> ctx.render(json(tasksResponse))
            );
    }
}
