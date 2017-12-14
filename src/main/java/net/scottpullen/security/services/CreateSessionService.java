package net.scottpullen.security.services;

import com.lambdaworks.crypto.SCryptUtil;
import io.reactivex.Single;
import net.scottpullen.common.exceptions.AuthenticationFailureException;
import net.scottpullen.security.commands.SessionCommand;
import net.scottpullen.security.entities.AuthenticationToken;
import net.scottpullen.users.entities.User;
import net.scottpullen.users.repositories.UserRepository;

import static net.scottpullen.common.ArgumentPreconditions.required;

public class CreateSessionService {

    private final TokenGeneratorService tokenGeneratorService;
    private final UserRepository userRepository;

    public CreateSessionService(final TokenGeneratorService tokenGeneratorService, final UserRepository userRepository) {
        required(tokenGeneratorService, "TokenGeneratorService required");
        required(userRepository, "UserRepository required");

        this.tokenGeneratorService = tokenGeneratorService;
        this.userRepository = userRepository;
    }

    public Single<AuthenticationToken> perform(SessionCommand command) {
        required(command, "SessionCommand required");
        
        return userRepository.findByEmail(command.getEmail())
            .flatMap(user -> verifyPassword(command, user));
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
