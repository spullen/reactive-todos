package net.scottpullen.handlers;

import com.google.common.net.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.Configuration;
import net.scottpullen.modules.ExecutorModule;
import net.scottpullen.services.AuthenticationService;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.Headers;
import ratpack.registry.Registry;
import ratpack.rx2.RxRatpack;

public class AuthenticationHandler implements Handler {

    private final Scheduler scheduler;
    private final AuthenticationService authenticationService;

    public AuthenticationHandler(final ExecutorModule executorModule, final AuthenticationService authenticationService) {
        this.scheduler = executorModule.getDataAndMessagingScheduler();
        this.authenticationService = authenticationService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        Headers headers = ctx.getRequest().getHeaders();

        if(!headers.contains(HttpHeaders.AUTHORIZATION)) {
            ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
        } else {
            String rawToken = headers.get(HttpHeaders.AUTHORIZATION);

            if(rawToken == null || rawToken.equals("") || !rawToken.startsWith(Configuration.BEARER_AUTHORIZATION_SCHEMA_KEY)) {
                ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
                return;
            }

            final String token = rawToken.replace(Configuration.BEARER_AUTHORIZATION_PREFIX, "");

            authenticationService.perform(token)
                .observeOn(scheduler)
                .toObservable()
                .compose(RxRatpack::bindExec)
                .subscribe(
                    maybeUser -> {
                        maybeUser.ifPresent(user -> ctx.next(Registry.single(user)));
                        ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
                    },
                    error -> {
                        ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
                    }
                );
        }
    }
}
