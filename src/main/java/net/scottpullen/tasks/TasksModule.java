package net.scottpullen.tasks;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.scottpullen.tasks.repositories.JooqTaskRepository;
import net.scottpullen.tasks.repositories.TaskRepository;

import javax.sql.DataSource;

public class TasksModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public TaskRepository getTaskRepository(final DataSource dataSource) {
        return new JooqTaskRepository(dataSource);
    }
}
