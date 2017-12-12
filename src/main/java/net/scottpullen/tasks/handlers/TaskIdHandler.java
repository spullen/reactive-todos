package net.scottpullen.tasks.handlers;

import io.netty.handler.codec.http.HttpResponseStatus;
import net.scottpullen.tasks.entities.TaskId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.registry.Registry;

public class TaskIdHandler implements Handler {
    @Override
    public void handle(Context ctx) throws Exception {
        try {
            TaskId taskId = new TaskId(ctx.getPathTokens().get("taskId"));
            ctx.next(Registry.single(taskId));
        } catch (Exception e) {
            ctx.clientError(HttpResponseStatus.BAD_REQUEST.code());
        }
    }
}
