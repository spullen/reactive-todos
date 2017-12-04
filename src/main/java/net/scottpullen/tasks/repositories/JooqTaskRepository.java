package net.scottpullen.tasks.repositories;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.DataAccessException;
import net.scottpullen.common.exceptions.UniqueConstraintException;
import net.scottpullen.tasks.entities.Task;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.tasks.entities.TaskStatus;
import net.scottpullen.tasks.tables.Tasks;
import net.scottpullen.users.entities.UserId;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.sql.BatchUpdateException;
import java.util.UUID;

import static net.scottpullen.common.constants.DatabaseExceptionCodes.UNIQUE_VIOLATION_CODE;
import static net.scottpullen.tasks.tables.Tasks.ID;

public class JooqTaskRepository implements TaskRepository {

    private final DSLContext jooq;

    public JooqTaskRepository(DataSource dataSource) {
        jooq = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Override
    public Single<TaskId> nextId() {
        return Single.create(subscriber -> {
            subscriber.onSuccess(new TaskId(UUID.randomUUID()));
        });
    }

    @Override
    public Completable create(Task task) {
        return Completable.create(subscriber -> {
            try {
                jooq.transaction(configuration -> {
                    DSLContext transaction = DSL.using(configuration);

                    transaction.insertInto(Tasks.TABLE)
                        .set(Tasks.ID, task.getId())
                        .set(Tasks.USER_ID, task.getUserId())
                        .set(Tasks.CONTENT, task.getContent())
                        .set(Tasks.NOTES, task.getNotes())
                        .set(Tasks.STATUS, task.getStatus())
                        .set(Tasks.PRIORITY, task.getPriority())
                        .set(Tasks.DUE_DATE, task.getDueDate())
                        .set(Tasks.COMPLETED_AT, task.getCompletedAt())
                        .set(Tasks.CREATED_AT, task.getCreatedAt())
                        .set(Tasks.UPDATED_AT, task.getUpdatedAt())
                        .execute();

                    subscriber.onComplete();
                });
            } catch(org.jooq.exception.DataAccessException e) {
                subscriber.onError(translateException(e));
            } catch(Exception e) {
                subscriber.onError(new DataAccessException("Error while creating Task: " + task.toString(), e));
            }
        });
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
                    jooq.select().from(Tasks.TABLE).where(ID.eq(taskId)).limit(1)
                );

                subscriber.onSuccess(exists);
            } catch (DataAccessException e) {
                throw e;
            } catch (Exception e) {
                subscriber.onError(new DataAccessException("Failed to check existence of task", e));
            }
        });
    }

    private Throwable translateException(final BatchUpdateException exception) {
        if (exception.getSQLState().equals(UNIQUE_VIOLATION_CODE)) {
            return new UniqueConstraintException("Unique constraint violated when saving task");
        } else {
            return new DataAccessException("Error in JooqTaskRepository", exception);
        }
    }

    private Throwable translateException(final org.jooq.exception.DataAccessException exception) {
        if (exception.getCause() instanceof BatchUpdateException) {
            return translateException((BatchUpdateException) exception.getCause());
        } else {
            return new DataAccessException("Error in JooqTaskRepository", exception);
        }
    }
}
