package net.scottpullen.users;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import net.scottpullen.security.services.TokenGeneratorService;
import net.scottpullen.users.handlers.RegistrationHandler;
import net.scottpullen.users.repositories.JooqUserRepository;
import net.scottpullen.users.repositories.UserRepository;
import net.scottpullen.users.services.RegistrationService;

import javax.sql.DataSource;

public class UsersModule extends AbstractModule {
    @Override
    public void configure() {
        bind(RegistrationChain.class).in(Scopes.SINGLETON);
        bind(RegistrationHandler.class).in(Scopes.SINGLETON);
        bind(UsersApiChain.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public UserRepository getUserRepository(final DataSource dataSource) {
        return new JooqUserRepository(dataSource);
    }

    @Provides
    @Singleton
    public RegistrationService getRegistrationService(final TokenGeneratorService tokenGeneratorService,
                                                      final UserRepository userRepository) {
        return new RegistrationService(tokenGeneratorService, userRepository);
    }
}
