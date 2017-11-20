package net.scottpullen.users.services;

import io.reactivex.Single;
import net.scottpullen.users.commands.RegistrationCommand;
import net.scottpullen.security.entities.AuthenticationToken;
import net.scottpullen.users.entities.User;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.users.repositories.UserRepository;
import net.scottpullen.security.services.PasswordHashingService;
import net.scottpullen.security.services.TokenGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private final TokenGeneratorService tokenGeneratorService;
    private final UserRepository userRepository;

    public RegistrationService(TokenGeneratorService tokenGeneratorService, UserRepository userRepository) {
        this.tokenGeneratorService = tokenGeneratorService;
        this.userRepository = userRepository;
    }

    public Single<Optional<AuthenticationToken>> perform(RegistrationCommand command) {
        UserId id = new UserId(UUID.randomUUID());
        LocalDateTime createdAt = LocalDateTime.now();

        log.warn("ID: {}, {}", id, createdAt);

        User user = new User(
            id,
            command.getEmail(),
            command.getFullName(),
            PasswordHashingService.perform(command.getPassword()),
            createdAt,
            createdAt
        );

        log.warn("USER: {}", user);

        // validate command
        // create user

        return userRepository.create(user)
            .toSingle(() -> tokenGeneratorService.perform(id));
    }
}
