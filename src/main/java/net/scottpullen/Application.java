package net.scottpullen;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import net.scottpullen.common.constants.DateTimeConstants;
import net.scottpullen.common.deserializers.TaskIdDeserializer;
import net.scottpullen.common.deserializers.UserIdDeserializer;
import net.scottpullen.common.entities.EntityId;
import net.scottpullen.common.serializers.EntityIdSerializer;
import net.scottpullen.database.DatabaseConfig;
import net.scottpullen.database.DatabaseModule;
import net.scottpullen.executor.ExecutorModule;
import net.scottpullen.tasks.TasksApiChain;
import net.scottpullen.tasks.TasksModule;
import net.scottpullen.tasks.entities.TaskId;
import net.scottpullen.users.UsersModule;
import net.scottpullen.users.entities.User;
import net.scottpullen.security.handlers.AuthorizationHandler;
import net.scottpullen.users.RegistrationApiChain;
import net.scottpullen.users.entities.UserId;
import net.scottpullen.security.SessionApiChain;
import net.scottpullen.security.JwtConfig;
import net.scottpullen.security.SecurityModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.guice.Guice;
import ratpack.rx2.RxRatpack;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone(DateTimeConstants.TIME_ZONE_ID));

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
                .module(ExecutorModule.class)
                .module(DatabaseModule.class)
                .module(SecurityModule.class)
                .module(UsersModule.class)
                .module(TasksModule.class)
                .add(ObjectMapper.class, new ObjectMapper()
                    .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
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
                .prefix("api/registration", RegistrationApiChain.class)
                .prefix("api/session", SessionApiChain.class)
                .prefix("api", api -> api
                    .all(AuthorizationHandler.class)
                    .prefix("tasks", TasksApiChain.class)
                    .get("test", ctx -> {
                        User user = ctx.get(User.class);
                        ctx.render("Here in API. Hello, " + user.getFullName());
                    })
                )
            );
        });
    }
}
