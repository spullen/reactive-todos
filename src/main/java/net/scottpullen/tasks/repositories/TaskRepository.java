package net.scottpullen.tasks.repositories;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.users.entities.UserId;

public interface TaskRepository {
    Single<TaskId> nextId();
    Single<Task> find(final TaskId id);
    Single<Task> create(final Task task);
    Single<Task> update(final Task task);
    Completable delete(final TaskId id);
    Observable<Task> findAllByTaskStatusAndByUserId(final TaskStatus status, final UserId userId);
    Single<Boolean> exists(TaskId taskId);
}
