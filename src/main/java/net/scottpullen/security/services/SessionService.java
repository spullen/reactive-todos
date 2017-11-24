package net.scottpullen.security.services;

import io.reactivex.Single;
import net.scottpullen.security.commands.SessionCommand;
import net.scottpullen.security.entities.AuthenticationToken;
import net.scottpullen.users.repositories.UserRepository;

import java.util.Optional;

public class SessionService {

    private final TokenGeneratorService tokenGeneratorService;
    private final UserRepository userRepository;

    public SessionService(final TokenGeneratorService tokenGeneratorService, final UserRepository userRepository) {
        this.tokenGeneratorService = tokenGeneratorService;
        this.userRepository = userRepository;
    }

    public Single<Optional<AuthenticationToken>> perform(SessionCommand command) {
        return Single.create(subscriber -> {

        });
    }

}
