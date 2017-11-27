package net.scottpullen.tasks.handlers;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class TasksHandler implements Handler {
    @Override
    public void handle(Context ctx) throws Exception {
        ctx.byMethod(methodSpec -> methodSpec
            .get(() -> {})
            .post(() -> {})
        );
    }
}
