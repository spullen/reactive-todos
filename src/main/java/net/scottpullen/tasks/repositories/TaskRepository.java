package net.scottpullen.tasks.repositories;

import io.reactivex.Completable;
import io.reactivex.Observable;
import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.users.entities.UserId;

public interface TaskRepository {
    Completable create(final Task task);
    Completable update(final Task task);
    Observable<Task> findAllByTaskStatusAndByUserId(final TaskStatus status, final UserId userId);
}
