package net.scottpullen.tasks;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import net.scottpullen.tasks.handlers.TaskHandler;
import net.scottpullen.tasks.handlers.TasksHandler;
import net.scottpullen.tasks.repositories.JooqTaskRepository;
import net.scottpullen.tasks.repositories.TaskRepository;

import javax.sql.DataSource;

public class TasksModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TasksApiChain.class).in(Scopes.SINGLETON);
        bind(TasksHandler.class).in(Scopes.SINGLETON);
        bind(TaskHandler.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public TaskRepository getTaskRepository(final DataSource dataSource) {
        return new JooqTaskRepository(dataSource);
    }
}
