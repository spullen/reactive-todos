package net.scottpullen;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.scottpullen.entities.User;
import net.scottpullen.handlers.AuthenticationHandler;
import net.scottpullen.handlers.RegistrationChain;
import net.scottpullen.handlers.RegistrationHandler;
import net.scottpullen.handlers.SessionChain;
import net.scottpullen.modules.DatabaseModule;
import net.scottpullen.modules.ExecutorModule;
import net.scottpullen.modules.ObjectMapperModule;
import net.scottpullen.repositories.JooqUserRepository;
import net.scottpullen.repositories.UserRepository;
import net.scottpullen.services.AuthenticationService;
import net.scottpullen.services.RegistrationService;
import net.scottpullen.services.SessionService;
import net.scottpullen.services.TokenGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.rx2.RxRatpack;
import ratpack.server.RatpackServer;

import java.util.TimeZone;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone(Configuration.TIME_ZONE_ID));

        Configuration configuration = Configuration.fromEnvironment();

        ExecutorModule executorModule = new ExecutorModule();
        ObjectMapperModule objectMapperModule = new ObjectMapperModule();
        DatabaseModule databaseModule = new DatabaseModule(configuration);

        TokenGeneratorService tokenGeneratorService = new TokenGeneratorService(
            Configuration.JWT_USER_OBJECT_ID_CLAIM,
            configuration.getJwtSigningKey(),
            Configuration.JWT_TTL
        );

        UserRepository userRepository = new JooqUserRepository(databaseModule.getDataSource());

        RegistrationService registrationService = new RegistrationService(tokenGeneratorService, userRepository);
        RegistrationHandler registrationHandler = new RegistrationHandler(executorModule, registrationService);
        RegistrationChain registrationChain = new RegistrationChain();

        SessionService sessionService = new SessionService(tokenGeneratorService, userRepository);
        SessionChain sessionChain = new SessionChain();

        AuthenticationService authenticationService = new AuthenticationService(configuration, userRepository);
        AuthenticationHandler authenticationHandler = new AuthenticationHandler(executorModule, authenticationService);

        RxRatpack.initialize();

        RatpackServer.start(spec -> {
            spec.registryOf(registry -> registry
                .add(ExecutorModule.class, executorModule)
                .add(DatabaseModule.class, databaseModule)
                .add(ObjectMapperModule.class, objectMapperModule)
                .add(ObjectMapper.class, objectMapperModule.getObjectMapper())
                .add(RegistrationHandler.class, registrationHandler)
                .add(RegistrationChain.class, registrationChain)
                .add(SessionChain.class, sessionChain)
                .add(AuthenticationHandler.class, authenticationHandler)
            );

            spec.handlers(chain -> chain
                .get("test/:name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
                .prefix("api/registration", RegistrationChain.class)
                .prefix("api/session", SessionChain.class)
                .prefix("api", api -> api
                    .all(AuthenticationHandler.class)
                    .get("test", ctx -> {
                        User user = ctx.get(User.class);
                        ctx.render("Here in API. Hello, " + user.getFullName());
                    })
                )
            );
        });
    }
}
