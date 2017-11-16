package net.scottpullen.services;

import io.jsonwebtoken.Jwts;
import net.scottpullen.Configuration;
import net.scottpullen.entities.AuthenticationToken;
import net.scottpullen.entities.User;

import java.util.Optional;

public class AuthenticationService {

    private final Configuration configuration;

    public AuthenticationService(final Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<User> perform(String token) {
        String userId = (String) Jwts.parser()
            .setSigningKey(configuration.getJwtSigningKey())
            .parseClaimsJws(token)
            .getBody()
            .get(Configuration.JWT_USER_OBJECT_ID_CLAIM);

        return Optional.empty();
    }
}
