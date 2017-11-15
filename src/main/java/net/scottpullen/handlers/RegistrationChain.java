package net.scottpullen.handlers;

import ratpack.func.Action;
import ratpack.handling.Chain;

public class RegistrationChain implements Action<Chain> {

    @Override
    public void execute(Chain chain) {
        chain.post("", RegistrationHandler.class);
    }
}
