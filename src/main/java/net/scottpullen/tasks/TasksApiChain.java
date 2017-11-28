package net.scottpullen.tasks;

import net.scottpullen.tasks.handlers.TaskCreateHandler;
import net.scottpullen.tasks.handlers.TaskDeleteHandler;
import net.scottpullen.tasks.handlers.TaskIndexHandler;
import net.scottpullen.tasks.handlers.TaskUpdateHandler;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class TasksApiChain implements Action<Chain> {
    @Override
    public void execute(Chain chain) throws Exception {
        chain.get(TaskIndexHandler.class)
            .post(TaskCreateHandler.class)
            .prefix(":taskId", taskChain -> taskChain
                // find task
                // authorize user/task operation
                .put(TaskUpdateHandler.class)
                .delete(TaskDeleteHandler.class)
            );
    }
}
