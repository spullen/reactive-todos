package net.scottpullen.handlers;

import io.reactivex.Scheduler;
import net.scottpullen.commands.RegistrationCommand;
import net.scottpullen.modules.ExecutorModule;
import net.scottpullen.services.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class RegistrationHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(RegistrationHandler.class);

    private final Scheduler scheduler;
    private final RegistrationService registrationService;

    public RegistrationHandler(ExecutorModule executorModule, RegistrationService registrationService) {
        this.scheduler = executorModule.getDataAndMessagingScheduler();
        this.registrationService = registrationService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.parse(fromJson(RegistrationCommand.class))
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
                        ctx.render("Failed...");
                    }
                },
                error -> ctx.error(error)
            );
    }
}
