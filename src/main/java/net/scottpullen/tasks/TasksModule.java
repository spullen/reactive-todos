package net.scottpullen.tasks;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import net.scottpullen.tasks.handlers.*;
import net.scottpullen.tasks.repositories.JooqTaskRepository;
import net.scottpullen.tasks.repositories.TaskRepository;
import net.scottpullen.tasks.services.TaskCreateService;
import net.scottpullen.tasks.services.TaskDeleteService;
import net.scottpullen.tasks.services.TaskUpdateService;

import javax.sql.DataSource;

public class TasksModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TasksApiChain.class).in(Scopes.SINGLETON);
        bind(TaskIndexHandler.class).in(Scopes.SINGLETON);
        bind(TaskCreateHandler.class).in(Scopes.SINGLETON);
        bind(TaskIdHandler.class).in(Scopes.SINGLETON);
        bind(TaskUpdateHandler.class).in(Scopes.SINGLETON);
        bind(TaskDeleteHandler.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public TaskRepository getTaskRepository(final DataSource dataSource) {
        return new JooqTaskRepository(dataSource);
    }

    @Provides
    @Singleton
    public TaskCreateService getTaskCreateService(final TaskRepository taskRepository) {
        return new TaskCreateService(taskRepository);
    }

    @Provides
    @Singleton
    public TaskUpdateService getTaskUpdateService(final TaskRepository taskRepository) {
        return new TaskUpdateService(taskRepository);
    }

    @Provides
    @Singleton
    public TaskDeleteService getTaskDeleteService(final TaskRepository taskRepository) {
        return new TaskDeleteService(taskRepository);
    }
}
