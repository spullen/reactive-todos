package net.scottpullen.modules;

import net.scottpullen.repositories.JooqUserRepository;
import net.scottpullen.repositories.UserRepository;

public class UserModule {

    private final UserRepository userRepository;

    public UserModule(DatabaseModule databaseModule) {
        userRepository = new JooqUserRepository(databaseModule.getDataSource());
        // TODO RegistrationService

        // profile update service?
    }
}
