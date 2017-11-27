package net.scottpullen.tasks;

import net.scottpullen.tasks.handlers.TaskHandler;
import net.scottpullen.tasks.handlers.TasksHandler;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class TasksApiChain implements Action<Chain> {
    @Override
    public void execute(Chain chain) throws Exception {
        /*
        This or what I have below?
        chain.get(ListTasksHandler.class);
        chain.post(CreateTaskHandler.class);
        chain.prefix(":userId", taskChain -> {
           chain.put(UpdateTaskHandler.class);
           chain.delete(DeleteTaskHandler.class);
        });
        */

        chain.path(TasksHandler.class);
        chain.path(":taskId", TaskHandler.class);
    }
}
