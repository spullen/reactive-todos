package net.scottpullen.services;

import io.reactivex.Single;
import net.scottpullen.commands.RegistrationCommand;
import net.scottpullen.entities.AuthenticationToken;
import net.scottpullen.entities.User;
import net.scottpullen.entities.UserId;
import net.scottpullen.repositories.UserRepository;
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
        return Single.create(subscriber -> {
            try {
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
                // hash password
                // generate user id
                // set created at, updated at on user
                // build user
                // create user
                // generate token
                // return token

                subscriber.onSuccess(tokenGeneratorService.perform(id));
            } catch(Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
