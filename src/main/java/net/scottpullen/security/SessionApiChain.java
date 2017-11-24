package net.scottpullen.security;

import net.scottpullen.security.handlers.SessionHandler;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class SessionApiChain implements Action<Chain> {
    @Override
    public void execute(Chain chain) throws Exception {
        chain.post("", SessionHandler.class);
    }
}
