package net.scottpullen.tasks.responses;

import net.scottpullen.tasks.entities.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tasks implements Serializable {
    int count;
    List<Task> tasks;

    public Tasks() {
        count = 0;
        tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
        count++;
    }
}
