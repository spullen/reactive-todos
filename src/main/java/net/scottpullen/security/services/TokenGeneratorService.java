package net.scottpullen.security.services;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.scottpullen.security.JwtConfig;
import net.scottpullen.security.SecurityConstants;
import net.scottpullen.security.entities.AuthenticationToken;
import net.scottpullen.users.entities.UserId;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import static net.scottpullen.common.ArgumentPreconditions.required;

public class TokenGeneratorService {

    private final JwtConfig jwtConfig;

    public TokenGeneratorService(final JwtConfig jwtConfig) {
        required(jwtConfig, "JwtConfig required");

        this.jwtConfig = jwtConfig;
    }

    public AuthenticationToken perform(UserId userId) {
        required(userId, "UserId required");

        String token = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuedAt(new Date())
            .setExpiration(Date.from(LocalDateTime.now().plusHours(jwtConfig.getTtl()).toInstant(ZoneOffset.UTC)))
            .claim(SecurityConstants.JWT_USER_OBJECT_ID_CLAIM, userId.toString())
            .signWith(SignatureAlgorithm.HS512, jwtConfig.getSigningKey())
            .compact();

        return new AuthenticationToken(token);
    }

}
