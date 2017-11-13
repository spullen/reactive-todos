package net.scottpullen.services;

import io.reactivex.Completable;
import net.scottpullen.commands.CreateRegistrationCommand;
import net.scottpullen.repositories.UserRepository;

public class RegistrationService {

    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Completable perform(CreateRegistrationCommand command) {
        return Completable.create(subscriber -> {

        });
    }
}
