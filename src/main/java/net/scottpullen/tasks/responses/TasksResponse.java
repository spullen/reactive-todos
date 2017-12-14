package net.scottpullen.tasks.responses;

import net.scottpullen.tasks.entities.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TasksResponse implements Serializable {
    Integer count;
    List<Task> tasks;

    public TasksResponse() {
        count = 0;
        tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
        count++;
    }
}
