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

public class AuthorizationHandler implements Handler {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationHandler.class);

    private final Scheduler scheduler;
    private final AuthorizationService authorizationService;

    @Inject
    public AuthorizationHandler(final Scheduler scheduler, final AuthorizationService authorizationService) {
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
                    maybeUser -> {
                        if(maybeUser.isPresent()) {
                            // TODO: provide a wrapper for AuthenticatedUser
                            ctx.next(Registry.single(maybeUser.get()));
                        } else {
                            ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
                        }
                    },
                    error -> ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code())
                );
        }
    }
}
