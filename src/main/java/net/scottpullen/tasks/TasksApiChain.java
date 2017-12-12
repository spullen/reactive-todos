package net.scottpullen.tasks;

import net.scottpullen.tasks.handlers.*;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class TasksApiChain implements Action<Chain> {
    @Override
    public void execute(Chain chain) throws Exception {
        chain.get(TaskIndexHandler.class)
            .post(TaskCreateHandler.class)
            .prefix(":taskId", taskChain -> taskChain
                .all(TaskIdHandler.class)
                .put(TaskUpdateHandler.class)
                .delete(TaskDeleteHandler.class)
            );
    }
}
