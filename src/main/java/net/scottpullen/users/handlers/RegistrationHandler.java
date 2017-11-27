package net.scottpullen.users.handlers;

import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.Scheduler;
import net.scottpullen.common.exceptions.ValidationException;
import net.scottpullen.users.commands.RegistrationCommand;
import net.scottpullen.users.services.RegistrationService;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class RegistrationHandler implements Handler {

    private final Scheduler scheduler;
    private final RegistrationService registrationService;

    @Inject
    public RegistrationHandler(Scheduler scheduler, RegistrationService registrationService) {
        this.scheduler = scheduler;
        this.registrationService = registrationService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.parse(fromJson(RegistrationCommand.class))
            .onError((Throwable t) -> ctx.clientError(HttpResponseStatus.BAD_REQUEST.code()))
            .to(RxRatpack::single)
            .observeOn(scheduler)
            .flatMap(registrationService::perform)
            .toObservable()
            .compose(RxRatpack::bindExec)
            .subscribe(
                maybeToken -> {
                    if(maybeToken.isPresent()) {
                        ctx.render(json(maybeToken.get()));
                    } else {
                        ctx.getResponse().status(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());
                        // Render something useful
                        ctx.render("Failed to register user");
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
