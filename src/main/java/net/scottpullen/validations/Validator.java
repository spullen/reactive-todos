package net.scottpullen.validations;

import io.reactivex.Completable;
import net.scottpullen.entities.User;
import net.scottpullen.entities.UserId;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;

public class Validator<T> {

    private final T target;

    public Validator(final T target) {
        this.target = target;
    }

    public Completable validate(final Consumer<ValidationContext> operation) {
        return Completable.create(subscriber -> {
            try {
                ValidationContext<T> context = new ValidationContext<T>(target);
                operation.accept(context);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public static void test() {
        User u = new User(
            new UserId(UUID.randomUUID()),
            "s.pullen05@gmail.com",
            "Scott Pullen",
            "password",
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        (new Validator<User>(u)).validate(validationContext -> {
            
        });
    }
}
