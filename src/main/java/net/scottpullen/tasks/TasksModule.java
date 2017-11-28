package net.scottpullen.tasks;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import net.scottpullen.tasks.handlers.TaskCreateHandler;
import net.scottpullen.tasks.handlers.TaskDeleteHandler;
import net.scottpullen.tasks.handlers.TaskIndexHandler;
import net.scottpullen.tasks.handlers.TaskUpdateHandler;
import net.scottpullen.tasks.repositories.JooqTaskRepository;
import net.scottpullen.tasks.repositories.TaskRepository;

import javax.sql.DataSource;

public class TasksModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TasksApiChain.class).in(Scopes.SINGLETON);
        bind(TaskIndexHandler.class).in(Scopes.SINGLETON);
        bind(TaskCreateHandler.class).in(Scopes.SINGLETON);
        bind(TaskUpdateHandler.class).in(Scopes.SINGLETON);
        bind(TaskDeleteHandler.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public TaskRepository getTaskRepository(final DataSource dataSource) {
        return new JooqTaskRepository(dataSource);
    }
}
