package net.scottpullen.handlers;

import net.scottpullen.services.RegistrationService;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class RegistrationChain implements Action<Chain> {

    private final RegistrationService registrationService;

    public RegistrationChain(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void execute(Chain chain) {
        
    }
}
