package net.scottpullen.users.repositories;

import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.NotFoundException;
import net.scottpullen.users.entities.User;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.common.exceptions.DataAccessException;
import net.scottpullen.common.exceptions.UniqueConstraintException;
import net.scottpullen.users.tables.Users;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.sql.BatchUpdateException;
import java.util.Optional;
import java.util.UUID;

import static net.scottpullen.common.ArgumentPreconditions.notBlank;
import static net.scottpullen.common.ArgumentPreconditions.required;
import static net.scottpullen.common.constants.DatabaseExceptionCodes.UNIQUE_VIOLATION_CODE;

public class JooqUserRepository implements UserRepository {

    private final DSLContext jooq;

    public JooqUserRepository(final DataSource dataSource) {
        required(dataSource, "DataSource required");

        jooq = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Override
    public Single<UserId> nextId() {
        return Single.create(subscriber -> {
            subscriber.onSuccess(new UserId(UUID.randomUUID()));
        });
    }

    @Override
    public Single<UserId> create(User user) {
        required(user, "User required");

        return Single.create(subscriber -> {
            try {
                jooq.transaction(configuration -> {
                    DSLContext transaction = DSL.using(configuration);

                    transaction.insertInto(Users.TABLE)
                        .set(Users.ID, user.getId().getValue())
                        .set(Users.EMAIL, user.getEmail())
                        .set(Users.FULL_NAME, user.getFullName())
                        .set(Users.PASSWORD_DIGEST, user.getPasswordDigest())
                        .set(Users.CREATED_AT, user.getCreatedAt())
                        .set(Users.UPDATED_AT, user.getUpdatedAt())
                        .execute();

                    subscriber.onSuccess(user.getId());
                });
            } catch(org.jooq.exception.DataAccessException e) {
                subscriber.onError(translateException(e));
            } catch(Exception e) {
                subscriber.onError(new DataAccessException("Error while creating User: " + user.toString(), e));
            }
        });
    }

    @Override
    public Completable update(User user) {
        required(user, "user required");

        return Completable.create(subscriber -> {
            try {
                jooq.transaction(configuration -> {
                    DSLContext transaction = DSL.using(configuration);

                    transaction.update(Users.TABLE)
                        .set(Users.FULL_NAME, user.getFullName())
                        .set(Users.PASSWORD_DIGEST, user.getPasswordDigest())
                        .set(Users.UPDATED_AT, user.getUpdatedAt())
                        .where(Users.ID.equal(user.getId()))
                        .execute();

                    subscriber.onComplete();
                });
            } catch(org.jooq.exception.DataAccessException e) {
                subscriber.onError(translateException(e));
            } catch(Exception e) {
                subscriber.onError(new DataAccessException("Error while updated User: " + user.toString(), e));
            }
        });
    }

    @Override
    public Single<User> findById(UserId id) {
        required(id, "UserId required");

        return Single.create(subscriber -> {
            try {
                Optional<User> maybeUser = jooq.select(
                        Users.ID,
                        Users.EMAIL,
                        Users.FULL_NAME,
                        Users.PASSWORD_DIGEST,
                        Users.CREATED_AT,
                        Users.UPDATED_AT
                    )
                    .from(Users.TABLE)
                    .where(Users.ID.equal(id))
                    .limit(1)
                    .fetchOptionalInto(User.class);

                if(maybeUser.isPresent()) {
                    subscriber.onSuccess(maybeUser.get());
                } else {
                    subscriber.onError(new NotFoundException("Failed to find user by id " + id));
                }
            } catch (DataAccessException e) {
                throw e;
            } catch (Exception e) {
                subscriber.onError(new DataAccessException("Failed to find user by id", e));
            }
        });
    }

    @Override
    public Single<User> findByEmail(String email) {
        required(email, "email required");
        notBlank(email, "email cannot be blank");

        return Single.create(subscriber -> {
            try {
                Optional<User> maybeUser = jooq.select(
                    Users.ID,
                    Users.EMAIL,
                    Users.FULL_NAME,
                    Users.PASSWORD_DIGEST,
                    Users.CREATED_AT,
                    Users.UPDATED_AT
                )
                .from(Users.TABLE)
                .where(Users.EMAIL.equal(email))
                .limit(1)
                .fetchOptionalInto(User.class);

                if(maybeUser.isPresent()) {
                    subscriber.onSuccess(maybeUser.get());
                } else {
                    subscriber.onError(new NotFoundException("Failed to find user by email " + email));
                }
            } catch (DataAccessException e) {
                throw e;
            } catch (Exception e) {
                subscriber.onError(new DataAccessException("Failed to find user by email", e));
            }
        });
    }

    @Override
    public Single<Boolean> existsById(UserId id) {
        required(id, "UserId required");

        return Single.create(subscriber -> {
            try {
                Boolean exists = jooq.fetchExists(
                    jooq.select().from(Users.TABLE).where(Users.ID.equal(id)).limit(1)
                );

                subscriber.onSuccess(exists);
            } catch (DataAccessException e) {
                throw e;
            } catch (Exception e) {
                subscriber.onError(new DataAccessException("Failed to check existence of user by id", e));
            }
        });
    }

    @Override
    public Single<Boolean> existsByEmail(String email) {
        required(email, "email required");
        notBlank(email, "email cannot be blank");

        return Single.create(subscriber -> {
            try {
                Boolean exists = jooq.fetchExists(
                    jooq.select().from(Users.TABLE).where(Users.EMAIL.equal(email)).limit(1)
                );

                subscriber.onSuccess(exists);
            } catch (DataAccessException e) {
                throw e;
            } catch (Exception e) {
                subscriber.onError(new DataAccessException("Failed to check existence of user by email", e));
            }
        });
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
