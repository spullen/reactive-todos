package net.scottpullen.repositories;

import io.reactivex.Completable;
import io.reactivex.Observable;
import net.scottpullen.entities.Task;
import net.scottpullen.entities.TaskStatus;
import net.scottpullen.entities.UserId;
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
}
