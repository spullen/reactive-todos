package net.scottpullen;

import net.scottpullen.database.DatabaseConfig;
import net.scottpullen.users.entities.User;
import net.scottpullen.security.handlers.AuthorizationHandler;
import net.scottpullen.users.chains.RegistrationChain;
import net.scottpullen.users.handlers.RegistrationHandler;
import net.scottpullen.security.chains.SessionChain;
import net.scottpullen.modules.DatabaseModule;
import net.scottpullen.modules.ExecutorModule;
import net.scottpullen.modules.ObjectMapperModule;
import net.scottpullen.users.repositories.JooqUserRepository;
import net.scottpullen.users.repositories.UserRepository;
import net.scottpullen.security.JwtConfig;
import net.scottpullen.security.SecurityModule;
import net.scottpullen.security.services.AuthorizationService;
import net.scottpullen.users.services.RegistrationService;
import net.scottpullen.security.services.SessionService;
import net.scottpullen.security.services.TokenGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.rx2.RxRatpack;
import ratpack.server.BaseDir;
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

        AuthorizationService authorizationService = new AuthorizationService(configuration, userRepository);
        AuthorizationHandler authenticationHandler = new AuthorizationHandler(executorModule, authorizationService);

        RxRatpack.initialize();

        RatpackServer.start(spec -> {
            spec.serverConfig(config -> {
                config.baseDir(BaseDir.find())
                    .yaml("database.yaml")
                    .yaml("jwt.yaml")
                    .env()
                    .sysProps()
                    .require("/db", DatabaseConfig.class)
                    .require("/jwt", JwtConfig.class);
            });

            spec.registry(Guice.registry(bindings -> bindings
                .add(DatabaseModule.class)
                .add(SecurityModule.class)
            ));

            /*
            spec.registryOf(registry -> registry
                .add(ExecutorModule.class, executorModule)
                .add(DatabaseModule.class, databaseModule)
                .add(ObjectMapperModule.class, objectMapperModule)
                .add(ObjectMapper.class, objectMapperModule.getObjectMapper())
                .add(RegistrationHandler.class, registrationHandler)
                .add(RegistrationChain.class, registrationChain)
                .add(SessionChain.class, sessionChain)
                .add(AuthorizationHandler.class, authenticationHandler)
            );
            */

            spec.handlers(chain -> chain
                .get("test/:name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
                .prefix("api/registration", RegistrationChain.class)
                .prefix("api/session", SessionChain.class)
                .prefix("api", api -> api
                    .all(AuthorizationHandler.class)
                    .get("test", ctx -> {
                        User user = ctx.get(User.class);
                        ctx.render("Here in API. Hello, " + user.getFullName());
                    })
                )
            );
        });
    }
}
