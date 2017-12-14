package net.scottpullen.security.handlers;

import com.google.common.net.HttpHeaders;
import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.security.SecurityConstants;
import net.scottpullen.security.services.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.Headers;
import ratpack.registry.Registry;
import ratpack.rx2.RxRatpack;

import static net.scottpullen.common.ArgumentPreconditions.required;

public class AuthorizationHandler implements Handler {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationHandler.class);

    private final Scheduler scheduler;
    private final AuthorizationService authorizationService;

    @Inject
    public AuthorizationHandler(final Scheduler scheduler, final AuthorizationService authorizationService) {
        required(scheduler, "Scheduler required");
        required(authorizationService, "AuthorizationService required");

        this.scheduler = scheduler;
        this.authorizationService = authorizationService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        Headers headers = ctx.getRequest().getHeaders();

        if(!headers.contains(HttpHeaders.AUTHORIZATION)) {
            ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
        } else {
            String rawToken = headers.get(HttpHeaders.AUTHORIZATION);

            if(rawToken == null || rawToken.equals("") || !rawToken.startsWith(SecurityConstants.BEARER_AUTHORIZATION_SCHEMA_KEY)) {
                ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
                return;
            }

            final String token = rawToken.replace(SecurityConstants.BEARER_AUTHORIZATION_PREFIX, "");

            authorizationService.perform(token)
                .observeOn(scheduler)
                .toObservable()
                .compose(RxRatpack::bindExec)
                .subscribe(
                    user -> ctx.next(Registry.single(user)),
                    error -> ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code())
                );
        }
    }
}
