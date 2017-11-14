package net.scottpullen.handlers;

import io.reactivex.Scheduler;
import net.scottpullen.commands.RegistrationCommand;
import net.scottpullen.modules.ExecutorModule;
import net.scottpullen.services.RegistrationService;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.rx2.RxRatpack;

import static ratpack.jackson.Jackson.fromJson;

public class RegistrationHandler implements Handler {

    private final Scheduler scheduler;
    private final RegistrationService registrationService;

    public RegistrationHandler(ExecutorModule executorModule, RegistrationService registrationService) {
        this.scheduler = executorModule.getDataAndMessagingScheduler();
        this.registrationService = registrationService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.parse(fromJson(RegistrationCommand.class))
            .to(RxRatpack::observe)
            .observeOn(scheduler)
            .compose(RxRatpack::bindExec)
            .subscribe(
                result -> ctx.render("HERE"),
                error -> ctx.error(error)
            );
    }
}
