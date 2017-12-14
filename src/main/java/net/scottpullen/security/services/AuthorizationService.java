package net.scottpullen.security.services;

import io.jsonwebtoken.Jwts;
import io.reactivex.Single;
import net.scottpullen.security.JwtConfig;
import net.scottpullen.users.entities.User;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.users.repositories.UserRepository;
import net.scottpullen.security.SecurityConstants;

import java.util.Optional;

import static net.scottpullen.common.ArgumentPreconditions.notBlank;
import static net.scottpullen.common.ArgumentPreconditions.required;

public class AuthorizationService {

    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    public AuthorizationService(final JwtConfig jwtConfig, final UserRepository userRepository) {
        required(jwtConfig, "JwtConfig required");
        required(userRepository, "UserRepository required");

        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }

    public Single<User> perform(final String token) {
        required(token, "token required");
        notBlank(token, "token cannot be blank");

        return Single.create(subscriber -> {
            try {
                String userIdFromToken = (String) Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .get(SecurityConstants.JWT_USER_OBJECT_ID_CLAIM);

                if(userIdFromToken != null && !userIdFromToken.equals("")) {
                    UserId userId = new UserId(userIdFromToken);
                    userRepository.findById(userId)
                        .subscribe(subscriber::onSuccess, subscriber::onError); // Is there a better way to compose this??
                } else {
                    // come up with a better exception for this
                    subscriber.onError(new IllegalArgumentException("Failed to parse user id from token"));
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
