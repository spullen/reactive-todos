package net.scottpullen.services;

import io.reactivex.Single;
import net.scottpullen.commands.RegistrationCommand;
import net.scottpullen.entities.AuthenticationToken;
import net.scottpullen.entities.UserId;
import net.scottpullen.exceptions.DataAccessException;
import net.scottpullen.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

                log.warn("ID: {}", id);

                // validate command
                // generate user id
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
