package net.scottpullen.services;

import io.reactivex.Completable;
import net.scottpullen.commands.RegistrationCommand;
import net.scottpullen.repositories.UserRepository;

public class RegistrationService {

    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Completable perform(RegistrationCommand command) {
        return Completable.create(subscriber -> {

        });
    }
}
