package net.scottpullen.services;

import net.scottpullen.commands.SessionCommand;
import net.scottpullen.repositories.UserRepository;

public class SessionService {

    private final TokenGeneratorService tokenGeneratorService;
    private final UserRepository userRepository;

    public SessionService(final TokenGeneratorService tokenGeneratorService, final UserRepository userRepository) {
        this.tokenGeneratorService = tokenGeneratorService;
        this.userRepository = userRepository;
    }

    public void perform(SessionCommand command) {
        // find user by email

    }

}
