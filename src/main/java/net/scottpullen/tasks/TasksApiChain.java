package net.scottpullen.tasks;

import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.handlers.*;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class TasksApiChain implements Action<Chain> {
    @Override
    public void execute(Chain chain) throws Exception {
        chain.path(ctx -> ctx.byMethod(byMethodSpec -> byMethodSpec
                .get(TaskIndexHandler.class)
                .post(TaskCreateHandler.class)
            ))
            .prefix(":taskId", taskChain -> taskChain
                .all(TaskIdHandler.class)
                .path(ctx -> ctx.byMethod(byMethodSpec -> byMethodSpec
                        .put(TaskUpdateHandler.class)
                        .delete(TaskDeleteHandler.class)
                    )
                )
            );
    }
}
