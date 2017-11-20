package net.scottpullen.database;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DatabaseModule extends AbstractModule {
    @Override
    public void configure() {
        //bind().in(Scopes.SINGLETON);
    }
}
