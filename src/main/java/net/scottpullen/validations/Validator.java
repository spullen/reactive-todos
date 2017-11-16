package net.scottpullen.validations;

import io.reactivex.Observable;

import java.util.function.BiConsumer;

public class Validator {

    public <T> Observable<T> validate(final BiConsumer operation) {
        return Observable.create(subscriber -> {
            // TODO
        });
    }
}
