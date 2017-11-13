package net.scottpullen;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.scottpullen.handlers.AuthenticationHandler;
import net.scottpullen.handlers.RegistrationChain;
import net.scottpullen.handlers.SessionChain;
import net.scottpullen.modules.DatabaseModule;
import net.scottpullen.modules.ExecutorModule;
import net.scottpullen.modules.ObjectMapperModule;
import net.scottpullen.repositories.JooqUserRepository;
import net.scottpullen.repositories.UserRepository;
import net.scottpullen.services.RegistrationService;
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

        UserRepository userRepository = new JooqUserRepository(databaseModule.getDataSource());

        RegistrationService registrationService = new RegistrationService(userRepository);

        RegistrationChain registrationChain = new RegistrationChain(registrationService);
        SessionChain sessionChain = new SessionChain();

        AuthenticationHandler authenticationHandler = new AuthenticationHandler();

        // TODO configure modules/action chains/handlers

        RxRatpack.initialize();

        RatpackServer.start(spec -> {
            spec.registryOf(registry -> registry
                .add(ExecutorModule.class, executorModule)
                .add(DatabaseModule.class, databaseModule)
                .add(ObjectMapperModule.class, objectMapperModule)
                .add(ObjectMapper.class, objectMapperModule.getObjectMapper())
                .add(RegistrationChain.class, registrationChain)
                .add(SessionChain.class, sessionChain)
                .add(AuthenticationHandler.class, authenticationHandler)
            );

            spec.handlers(chain -> chain
                .prefix("api/registration", RegistrationChain.class)
                .prefix("api/session", SessionChain.class)

                .prefix("api", api -> api
                    .all(AuthenticationHandler.class)
                )

                .get(ctx -> ctx.render("Hello World!"))
                .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
            );
        });
    }
}
