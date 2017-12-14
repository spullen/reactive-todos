package net.scottpullen.security.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.common.exceptions.AuthenticationFailureException;
import net.scottpullen.common.exceptions.NotFoundException;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.security.commands.SessionCommand;
import net.scottpullen.security.services.CreateSessionService;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static net.scottpullen.common.ArgumentPreconditions.required;
import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class SessionHandler implements Handler {

    private final Scheduler scheduler;
    private final CreateSessionService sessionService;

    @Inject
    public SessionHandler(Scheduler scheduler, CreateSessionService sessionService) {
        required(scheduler, "Scheduler required");
        required(sessionService, "SessionService required");
        
        this.scheduler = scheduler;
        this.sessionService = sessionService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.parse(fromJson(SessionCommand.class))
            .onError((Throwable t) -> ctx.clientError(HttpResponseStatus.BAD_REQUEST.code()))
            .to(RxRatpack::single)
            .observeOn(scheduler)
            .flatMap(sessionService::perform)
            .toObservable()
            .compose(RxRatpack::bindExec)
            .subscribe(
                authenticationToken -> ctx.render(json(authenticationToken)),
                error -> {
                    if(error instanceof ValidationException) {
                        ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                        ctx.render(json(((ValidationException) error).getValidationResult()));
                    } else if(error instanceof AuthenticationFailureException || error instanceof NotFoundException) {
                        ctx.clientError(HttpResponseStatus.UNAUTHORIZED.code());
                    } else {
                        ctx.clientError(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                    }
                }
            );
    }
}
