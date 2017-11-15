package net.scottpullen.services;

import io.reactivex.Single;
import net.scottpullen.commands.RegistrationCommand;
import net.scottpullen.entities.AuthenticationToken;
import net.scottpullen.entities.UserId;
import net.scottpullen.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

public class RegistrationService {

    private final TokenGeneratorService tokenGeneratorService;
    private final UserRepository userRepository;

    public RegistrationService(TokenGeneratorService tokenGeneratorService, UserRepository userRepository) {
        this.tokenGeneratorService = tokenGeneratorService;
        this.userRepository = userRepository;
    }

    public Single<Optional<AuthenticationToken>> perform(RegistrationCommand command) {
        return Single.create(subscriber -> {
            try {
                subscriber.onSuccess(tokenGeneratorService.perform(new UserId(UUID.randomUUID())));
            } catch(Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
