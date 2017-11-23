package net.scottpullen.common.scratchvalidations;

import io.reactivex.Completable;

import java.util.function.Consumer;

/**
 * Scratch work (see evernote for notes on thoughts)
 *
 * General idea is to provide something like FluentValidator, but slightly more flexible using functional programming
 *
 * @param <T>
 */
public class Validation<T> {
    private final T target;

    public Validation(final T target) {
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
}
