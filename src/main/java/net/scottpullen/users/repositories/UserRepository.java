package net.scottpullen.users.repositories;

import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.users.entities.User;
import net.scottpullen.users.entities.UserId;

import java.util.Optional;

public interface UserRepository {
    Single<UserId> nextId();
    Single<UserId> create(final User user);
    Completable update(final User user);
    Single<Optional<User>> findById(final UserId id);
    Single<Optional<User>> findByEmail(final String email);
    Single<Boolean> existsById(final UserId id);
    Single<Boolean> existsByEmail(final String email);
}
