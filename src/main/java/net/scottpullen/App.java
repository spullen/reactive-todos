package net.scottpullen;

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
        TimeZone.setDefault(Configuration.TIME_ZONE_ID);

        Configuration configuration = Configuration.fromEnvironment();

        RxRatpack.initialize();

        RatpackServer.start(spec -> {
            spec.handlers(chain -> {
                chain
                    .get(ctx -> ctx.render("Hello World!"))
                    .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"));
            });
        });
    }
}
