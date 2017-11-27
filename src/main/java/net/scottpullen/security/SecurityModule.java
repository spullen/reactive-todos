package net.scottpullen.security;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import net.scottpullen.security.handlers.AuthorizationHandler;
import net.scottpullen.security.handlers.SessionHandler;
import net.scottpullen.security.services.AuthorizationService;
import net.scottpullen.security.services.CreateSessionService;
import net.scottpullen.security.services.TokenGeneratorService;
import net.scottpullen.users.repositories.UserRepository;

public class SecurityModule extends AbstractModule {
    @Override
    public void configure() {
        bind(SessionApiChain.class).in(Scopes.SINGLETON);
        bind(SessionHandler.class).in(Scopes.SINGLETON);
        bind(AuthorizationHandler.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public TokenGeneratorService getTokenGeneratorService(final JwtConfig jwtConfig) {
        return new TokenGeneratorService(jwtConfig);
    }

    @Provides
    @Singleton
    public CreateSessionService getSessionService(final TokenGeneratorService tokenGeneratorService,
                                                  final UserRepository userRepository) {
        return new CreateSessionService(tokenGeneratorService, userRepository);
    }

    @Provides
    @Singleton
    public AuthorizationService getAuthorizationService(final JwtConfig jwtConfig,
                                                        final UserRepository userRepository) {
        return new AuthorizationService(jwtConfig, userRepository);
    }
}
