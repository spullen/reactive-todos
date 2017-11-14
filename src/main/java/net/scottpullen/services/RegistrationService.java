package net.scottpullen.services;

import io.reactivex.Single;
import net.scottpullen.commands.RegistrationCommand;
import net.scottpullen.entities.AuthenticationToken;
import net.scottpullen.repositories.UserRepository;

import java.util.Optional;

public class RegistrationService {

    private final UserRepository userRepository;

    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<Optional<AuthenticationToken>> perform(RegistrationCommand command) {
        return Single.create(subscriber -> {

        });
    }
}
