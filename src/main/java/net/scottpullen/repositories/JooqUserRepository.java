package net.scottpullen.repositories;

import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.entities.User;
import net.scottpullen.entities.UserId;
import net.scottpullen.exceptions.DataAccessException;
import net.scottpullen.exceptions.UniqueConstraintException;
import org.jooq.DSLContext;
import org.jooq.Package;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.sql.BatchUpdateException;
import java.util.Optional;

import static net.scottpullen.constants.DatabaseExceptionCodes.UNIQUE_VIOLATION_CODE;

public class JooqUserRepository implements UserRepository {

    private final DSLContext jooq;

    public JooqUserRepository(final DataSource dataSource) {
        jooq = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Override
    public Completable create(User user) {
        return Completable.create(subscriber -> {
            try {
                // TODO insert

                subscriber.onComplete();
            } catch(org.jooq.exception.DataAccessException e) {
                subscriber.onError(translateException(e));
            } catch(Exception e) {
                subscriber.onError(new DataAccessException("Error while creating User: " + user.toString(), e));
            }
        });
    }

    @Override
    public Completable update(User user) {
        return Completable.create(subscriber -> {
            try {
                // TODO update

                subscriber.onComplete();
            } catch(org.jooq.exception.DataAccessException e) {
                subscriber.onError(translateException(e));
            } catch(Exception e) {
                subscriber.onError(new DataAccessException("Error while updated User: " + user.toString(), e));
            }
        });
    }

    @Override
    public Single<Optional<User>> findById(UserId id) {
        return null;
    }

    @Override
    public Single<Optional<User>> findByEmail(String email) {
        return null;
    }

    @Override
    public Single<Boolean> existsById(UserId id) {
        return null;
    }

    @Override
    public Single<Boolean> existsByEmail(String email) {
        return null;
    }

    private Throwable translateException(final BatchUpdateException exception) {
        if (exception.getSQLState().equals(UNIQUE_VIOLATION_CODE)) {
            return new UniqueConstraintException("Unique constraint violated when saving user");
        } else {
            return new DataAccessException("Error in JooqUserRepository", exception);
        }
    }

    private Throwable translateException(final org.jooq.exception.DataAccessException exception) {
        if (exception.getCause() instanceof BatchUpdateException) {
            return translateException((BatchUpdateException) exception.getCause());
        } else {
            return new DataAccessException("Error in JooqUserRepository", exception);
        }
    }
}
