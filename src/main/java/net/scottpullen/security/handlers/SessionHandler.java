package net.scottpullen.security.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.security.commands.SessionCommand;
import net.scottpullen.security.services.CreateSessionService;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class SessionHandler implements Handler {

    private final Scheduler scheduler;
    private final CreateSessionService sessionService;

    @Inject
    public SessionHandler(Scheduler scheduler, CreateSessionService sessionService) {
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
                maybeToken -> {
                    if(maybeToken.isPresent()) {
                        ctx.render(json(maybeToken.get()));
                    } else {
                        ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                        // Render something useful
                        ctx.render("Failed to start session");
                    }
                },
                error -> {
                    if(error instanceof ValidationException) {
                        ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                        ctx.render(json(((ValidationException) error).getValidationResult()));
                    } else {
                        ctx.clientError(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                    }
                }
            );
    }
}
