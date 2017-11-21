package net.scottpullen.security.services;

import io.jsonwebtoken.Jwts;
import io.reactivex.Single;
import net.scottpullen.security.JwtConfig;
import net.scottpullen.users.entities.User;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.users.repositories.UserRepository;
import net.scottpullen.security.SecurityConstants;

import java.util.Optional;

public class AuthorizationService {

    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    public AuthorizationService(final JwtConfig jwtConfig, final UserRepository userRepository) {
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }

    public Single<Optional<User>> perform(final String token) {
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
                    subscriber.onSuccess(Optional.empty());
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
