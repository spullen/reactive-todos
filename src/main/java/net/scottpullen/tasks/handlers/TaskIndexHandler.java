package net.scottpullen.tasks.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.users.entities.User;
import ratpack.handling.Context;
import ratpack.handling.Handler;

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

        taskRepository.findAllByTaskStatusAndByUserId(TaskStatus.PENDING, currentUser.getId())
            .subscribe(
                task -> {
                    // add to list?
                    // stream to client?
                },
                error -> {
                    ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                },
                () -> {
                    ctx.render("Complete");
                });
    }
}
