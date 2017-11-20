package net.scottpullen.security.services;

import net.scottpullen.security.commands.SessionCommand;
import net.scottpullen.users.repositories.UserRepository;

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
