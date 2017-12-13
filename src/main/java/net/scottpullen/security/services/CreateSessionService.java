package net.scottpullen.security.services;

import com.lambdaworks.crypto.SCryptUtil;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.AuthenticationFailureException;
import net.scottpullen.common.exceptions.NotFoundException;
import net.scottpullen.security.commands.SessionCommand;
import net.scottpullen.security.entities.AuthenticationToken;
import net.scottpullen.users.entities.User;
import net.scottpullen.users.repositories.UserRepository;

import java.util.Optional;

public class CreateSessionService {

    private final TokenGeneratorService tokenGeneratorService;
    private final UserRepository userRepository;

    public CreateSessionService(final TokenGeneratorService tokenGeneratorService, final UserRepository userRepository) {
        this.tokenGeneratorService = tokenGeneratorService;
        this.userRepository = userRepository;
    }

    public Single<AuthenticationToken> perform(SessionCommand command) {
        return userRepository.findByEmail(command.getEmail())
            .flatMap(this::verifyUser)
            .flatMap((user) -> verifyPassword(command, user));
    }


    private Single<User> verifyUser(Optional<User> maybeUser) {
        return Single.create(subscriber -> {
            if(maybeUser.isPresent()) {
                subscriber.onSuccess(maybeUser.get());
            } else {
                subscriber.onError(new NotFoundException("Failed to find user for given email."));
            }
        });
    }

    private Single<AuthenticationToken> verifyPassword(SessionCommand command, User user) {
        return Single.create(subscriber -> {
            boolean validPassword = SCryptUtil.check(command.getPassword(), user.getPasswordDigest());

            if(validPassword) {
                subscriber.onSuccess(tokenGeneratorService.perform(user.getId()));
            } else {
                subscriber.onError(new AuthenticationFailureException());
            }
        });
    }
}
