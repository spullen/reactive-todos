package net.scottpullen.tasks.repositories;

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
    public Single<Task> create(Task task) {
        return Single.create(subscriber -> {
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
                        .set(Tasks.DUE_DATE, task.getDueDate().orElse(null))
                        .set(Tasks.COMPLETED_AT, task.getCompletedAt().orElse(null))
                        .set(Tasks.CREATED_AT, task.getCreatedAt())
                        .set(Tasks.UPDATED_AT, task.getUpdatedAt())
                        .execute();

                    subscriber.onSuccess(task);
                });
            } catch(org.jooq.exception.DataAccessException e) {
                subscriber.onError(translateException(e));
            } catch(Exception e) {
                subscriber.onError(new DataAccessException("Error while creating Task: " + task.toString(), e));
            }
        });
    }

    @Override
    public Single<Task> update(Task task) {
        return Single.create(subscriber -> {
            try {
                jooq.transaction(configuration -> {
                    DSLContext transaction = DSL.using(configuration);

                    transaction.update(Tasks.TABLE)
                        .set(Tasks.CONTENT, task.getContent())
                        .set(Tasks.NOTES, task.getNotes())
                        .set(Tasks.STATUS, task.getStatus())
                        .set(Tasks.PRIORITY, task.getPriority())
                        .set(Tasks.DUE_DATE, task.getDueDate().orElse(null))
                        .set(Tasks.COMPLETED_AT, task.getCompletedAt().orElse(null))
                        .set(Tasks.UPDATED_AT, task.getUpdatedAt())
                        .where(Tasks.ID.eq(task.getId()))
                        .execute();

                    subscriber.onSuccess(task);
                });
            } catch(org.jooq.exception.DataAccessException e) {
                subscriber.onError(translateException(e));
            } catch(Exception e) {
                subscriber.onError(new DataAccessException("Error while updating Task: " + task.toString(), e));
            }
        });
    }

    @Override
    public Observable<Task> findAllByTaskStatusAndByUserId(TaskStatus status, UserId userId) {
        return Observable.create(subscriber -> {
            try {
                jooq.select(
                        Tasks.ID,
                        Tasks.USER_ID,
                        Tasks.CONTENT,
                        Tasks.NOTES,
                        Tasks.STATUS,
                        Tasks.PRIORITY,
                        Tasks.DUE_DATE,
                        Tasks.COMPLETED_AT,
                        Tasks.CREATED_AT,
                        Tasks.UPDATED_AT
                    )
                    .from(Tasks.TABLE)
                    .where(Tasks.STATUS.eq(status).and(Tasks.USER_ID.eq(userId)))
                    .orderBy(Tasks.DUE_DATE, Tasks.PRIORITY.desc())
                    .fetchStream()
                    .map(record -> {
                        return Task.builder()
                            .withId(record.get(Tasks.ID))
                            .withUserId(record.get(Tasks.USER_ID))
                            .withContent(record.get(Tasks.CONTENT))
                            .withNotes(record.get(Tasks.NOTES))
                            .withStatus(record.get(Tasks.STATUS))
                            .withPriority(record.get(Tasks.PRIORITY))
                            .withDueDate(record.get(Tasks.DUE_DATE))
                            .withCompletedAt(record.get(Tasks.COMPLETED_AT))
                            .withCreatedAt(record.get(Tasks.CREATED_AT))
                            .withUpdatedAt(record.get(Tasks.UPDATED_AT))
                            .build();
                    })
                    .forEach(subscriber::onNext);

                subscriber.onComplete();
            } catch(org.jooq.exception.DataAccessException e) {
                subscriber.onError(translateException(e));
            } catch(Exception e) {
                subscriber.onError(new DataAccessException("Error while fetching tasks for Status " + status + " User " + userId, e));
            }
        });
    }

    @Override
    public Single<Boolean> exists(TaskId taskId) {
        return Single.create(subscriber -> {
            try {
                Boolean exists = jooq.fetchExists(
                    jooq.select(Tasks.ID).from(Tasks.TABLE).where(ID.eq(taskId)).limit(1)
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
