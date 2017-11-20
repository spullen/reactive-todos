package net.scottpullen.security.services;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.scottpullen.security.entities.AuthenticationToken;
import net.scottpullen.users.entities.UserId;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

public class TokenGeneratorService {

    private final String objectIdClaim;
    private final String signingKey;
    private final Integer ttl;

    public TokenGeneratorService(String objectIdClaim, String signingKey, Integer ttl) {
        this.objectIdClaim = objectIdClaim;
        this.signingKey = signingKey;
        this.ttl = ttl;
    }

    public Optional<AuthenticationToken> perform(UserId userId) {
        try {
            String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusHours(ttl).toInstant(ZoneOffset.UTC)))
                .claim(objectIdClaim, userId.toString())
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();

            return Optional.of(new AuthenticationToken(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
