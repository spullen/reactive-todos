package net.scottpullen.security.entities;

public class AuthenticationToken {
    private final String token;

    public AuthenticationToken(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
