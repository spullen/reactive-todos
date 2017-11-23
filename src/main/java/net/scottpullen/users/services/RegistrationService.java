package net.scottpullen.users.services;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.users.commands.RegistrationCommand;
import net.scottpullen.security.entities.AuthenticationToken;
import net.scottpullen.users.entities.User;
import net.scottpullen.users.repositories.UserRepository;
import net.scottpullen.security.services.PasswordHashingService;
import net.scottpullen.security.services.TokenGeneratorService;
import net.scottpullen.users.validators.RegistrationCommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toComplex;

public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private final TokenGeneratorService tokenGeneratorService;
    private final UserRepository userRepository;

    public RegistrationService(TokenGeneratorService tokenGeneratorService, UserRepository userRepository) {
        this.tokenGeneratorService = tokenGeneratorService;
        this.userRepository = userRepository;
    }

    public Single<Optional<AuthenticationToken>> perform(RegistrationCommand command) {
        return Single.just(command)
            .flatMap(this::validateCommand)
            .flatMap(this::buildUser)
            .flatMap(userRepository::create)
            .map(tokenGeneratorService::perform);
    }

    private Single<RegistrationCommand> validateCommand(RegistrationCommand command) {
        return Single.create(subscriber -> {
            try {
                ComplexResult validationResult = FluentValidator.checkAll()
                    .on(command, new RegistrationCommandValidator())
                    .doValidate()
                    .result(toComplex());

                if(validationResult.isSuccess()) {
                    subscriber.onSuccess(command);
                } else {
                    subscriber.onError(new ValidationException(validationResult));
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    private Single<User> buildUser(RegistrationCommand command) {
        return userRepository.nextId()
            .map(userId -> {
                LocalDateTime createdAt = LocalDateTime.now();

                return new User(
                    userId,
                    command.getEmail(),
                    command.getFullName(),
                    PasswordHashingService.perform(command.getPassword()),
                    createdAt,
                    createdAt
                );
            });
    }
}
