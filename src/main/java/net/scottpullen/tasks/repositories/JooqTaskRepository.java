package net.scottpullen.tasks.repositories;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.DataAccessException;
import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.tables.Tasks;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.users.tables.Users;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;

public class JooqTaskRepository implements TaskRepository {

    private final DSLContext jooq;

    public JooqTaskRepository(DataSource dataSource) {
        jooq = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Override
    public Single<TaskId> nextId() {
        return Single.create(subscriber -> {

        });
    }

    @Override
    public Completable create(Task task) {
        return null;
    }

    @Override
    public Completable update(Task task) {
        return null;
    }

    @Override
    public Observable<Task> findAllByTaskStatusAndByUserId(TaskStatus status, UserId userId) {
        return null;
    }

    @Override
    public Single<Boolean> exists(TaskId taskId) {
        return Single.create(subscriber -> {
            try {
                Boolean exists = jooq.fetchExists(
                    jooq.select().from(Tasks.TABLE).where(Tasks.ID.eq(taskId)).limit(1)
                );

                subscriber.onSuccess(exists);
            } catch (DataAccessException e) {
                throw e;
            } catch (Exception e) {
                subscriber.onError(new DataAccessException("Failed to check existence of task", e));
            }
        });
    }
}
