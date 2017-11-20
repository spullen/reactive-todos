package net.scottpullen.modules;

import net.scottpullen.users.repositories.JooqUserRepository;
import net.scottpullen.users.repositories.UserRepository;

public class UserModule {

    private final UserRepository userRepository;

    public UserModule(DatabaseModule databaseModule) {
        userRepository = new JooqUserRepository(databaseModule.getDataSource());
        // TODO RegistrationService

        // profile update service?
    }
}
