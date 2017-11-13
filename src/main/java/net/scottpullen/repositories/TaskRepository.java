package net.scottpullen.repositories;

import io.reactivex.Completable;
import io.reactivex.Observable;
import net.scottpullen.entities.Task;
import net.scottpullen.entities.TaskStatus;
import net.scottpullen.entities.UserId;

public interface TaskRepository {
    Completable create(final Task task);
    Completable update(final Task task);
    Observable<Task> findAllByTaskStatusAndByUserId(final TaskStatus status, final UserId userId);
}
