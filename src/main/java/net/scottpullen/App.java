package net.scottpullen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import net.scottpullen.common.deserializers.TaskIdDeserializer;
import net.scottpullen.common.deserializers.UserIdDeserializer;
import net.scottpullen.common.entities.EntityId;
import net.scottpullen.common.serializers.EntityIdSerializer;
import net.scottpullen.database.DatabaseConfig;
import net.scottpullen.database.DatabaseModule;
import net.scottpullen.tasks.TasksModule;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.users.UsersModule;
import net.scottpullen.users.entities.User;
import net.scottpullen.security.handlers.AuthorizationHandler;
import net.scottpullen.users.chains.RegistrationChain;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.users.handlers.RegistrationHandler;
import net.scottpullen.security.chains.SessionChain;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone(Configuration.TIME_ZONE_ID));

        /*
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
        */

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
                .module(DatabaseModule.class)
                .module(SecurityModule.class)
                .module(UsersModule.class)
                .module(TasksModule.class)
                .add(ObjectMapper.class, new ObjectMapper()
                    .registerModule(new Jdk8Module())
                    .registerModule(new SimpleModule()
                        .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE)
                        .addSerializer(EntityId.class, EntityIdSerializer.INSTANCE)
                        .addDeserializer(UserId.class, UserIdDeserializer.INSTANCE)
                        .addDeserializer(TaskId.class, TaskIdDeserializer.INSTANCE)
                    ))
            ));

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
