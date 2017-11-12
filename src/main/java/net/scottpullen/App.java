package net.scottpullen;

import net.scottpullen.modules.DatabaseModule;
import net.scottpullen.modules.ExecutorModule;
import net.scottpullen.modules.ObjectMapperModule;
import net.scottpullen.repositories.JooqUserRepository;
import net.scottpullen.repositories.UserRepository;
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

        // TODO configure modules

        RxRatpack.initialize();

        RatpackServer.start(spec -> {
            spec.registryOf(registry -> registry
                .add(ExecutorModule.class, executorModule)
                .add(DatabaseModule.class, databaseModule)
                .add(ObjectMapperModule.class, objectMapperModule)
            );

            spec.handlers(chain -> chain
                .get(ctx -> ctx.render("Hello World!"))
                .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
            );
        });
    }
}
