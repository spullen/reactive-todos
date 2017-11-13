package net.scottpullen.repositories;

import io.reactivex.Completable;
import io.reactivex.Single;
import net.scottpullen.entities.User;
import net.scottpullen.entities.UserId;

import java.util.Optional;

public interface UserRepository {
    Completable create(final User user);
    Completable update(final User user);
    Single<Optional<User>> findById(final UserId id);
    Single<Optional<User>> findByEmail(final String email);
    Single<Boolean> existsById(final UserId id);
    Single<Boolean> existsByEmail(final String email);
}
