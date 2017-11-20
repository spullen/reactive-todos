package net.scottpullen.users.chains;

import net.scottpullen.users.handlers.RegistrationHandler;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class RegistrationChain implements Action<Chain> {

    @Override
    public void execute(Chain chain) {
        chain.post("", RegistrationHandler.class);
    }
}